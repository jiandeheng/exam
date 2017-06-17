package com.lanouit.exam.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.PositionIllegalException;
import com.lanouit.exam.exception.UserActivateException;
import com.lanouit.exam.exception.UserNotFoundException;
import com.lanouit.exam.exception.UserSendRegisterMailException;
import com.lanouit.exam.exception.UsermailExistException;
import com.lanouit.exam.mapper.PositionMapper;
import com.lanouit.exam.mapper.UserMapper;
import com.lanouit.exam.po.User;
import com.lanouit.exam.po.UserExample;
import com.lanouit.exam.po.UserExample.Criteria;
import com.lanouit.exam.service.UserService;
import com.lanouit.exam.util.mail.Mail;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	UserMapper userMapper;

	@Resource
	PositionMapper positionMapper;

	@Override
	public User login(User user) {
		// 验证账号密码是否正确
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(user.getUserAccount());
		criteria.andUserPasswordEqualTo(DigestUtils.md5Hex(user
				.getUserPassword()));
		criteria.andUserStatusEqualTo(1);
		List<User> list = userMapper.selectByExampleWithBLOBs(userExample);
		if (list.isEmpty())
			return null;
		user = list.get(0);
		return user;
	}

	@Override
	@Transactional
	public User register(User user) throws UsermailExistException,
			UserSendRegisterMailException {
		// 检验邮箱是否被注册
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(user.getUserMail());
		criteria.andUserStatusEqualTo(1);
		if (!userMapper.selectByExampleWithBLOBs(userExample).isEmpty()) {
			// 邮箱已被注册，向上抛出异常
			throw new UsermailExistException("mail exist");
		}
		// 生成user_createtime
		Date userCreatetime = new Date();
		user.setUserCreatetime(userCreatetime);
		// 生成user_token
		String userToken = user.getUserMail() + userCreatetime.getTime();
		userToken = DigestUtils.md5Hex(userToken);
		user.setUserToken(userToken);
		user.setUserStatus(0);
		user.setUserPic("");
		user.setUserPassword(DigestUtils.md5Hex(user.getUserPassword()));
		// 添加操作
		userMapper.insertSelective(user);
		// 发送用户注册激活邮件
		Mail mail = new Mail();
		try {
			mail.sendRegisterEmail(user);
		} catch (Exception e) {
			e.printStackTrace();
			// 如果发送激活邮件异常，则向上抛出新异常
			throw new UserSendRegisterMailException(
					"send user's register mail error");
		}
		return user;
	}

	@Override
	@Transactional
	public User activateUser(User user) throws UserActivateException,
			UsermailExistException {
		// 根据user_id，user_token检查是否有该记录
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andIdEqualTo(user.getId());
		criteria.andUserTokenEqualTo(user.getUserToken());
		List<User> list = userMapper.selectByExampleWithBLOBs(userExample);
		// 不存在，抛出激活失效异常
		if (list.isEmpty()) {
			throw new UserActivateException("user activate exception");
		}
		// 检查该邮箱是否已被激活
		user = list.get(0);
		userExample = new UserExample();
		criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(user.getUserMail());
		criteria.andUserStatusEqualTo(1);
		// 已被激活，抛出账户已存在异常
		if (!userMapper.selectByExampleWithBLOBs(userExample).isEmpty()) {
			throw new UsermailExistException("mail exist");
		}
		// 设置user_status,user_account,更新信息,返回user对象
		user.setUserStatus(1);
		user.setUserToken("");
		user.setUserAccount(user.getUserMail());
		userMapper.updateByPrimaryKeyWithBLOBs(user);
		return user;
	}

	@Override
	public User update(User user) throws PositionIllegalException {
		// 该职业不存在
		if (this.positionMapper.selectByPrimaryKey(user.getUserPosition()) == null) {
			throw new PositionIllegalException("不合法职业");
		}
		this.userMapper.updateByPrimaryKeySelective(user);
		user = this.userMapper.selectByPrimaryKey(user.getId());
		return user;
	}

	@Override
	public User updatePassword(User user) {
		String oldPassword = DigestUtils.md5Hex(user.getUserPassword());
		String newPassword = DigestUtils.md5Hex(user.getNewUserPassword());
		user = this.userMapper.selectByPrimaryKey(user.getId());
		// 旧密码不正确
		if (!oldPassword.equals(user.getUserPassword())) {
			return null;
		}
		// 旧密码正确，设置新密码
		user.setUserPassword(newPassword);
		this.userMapper.updateByPrimaryKeySelective(user);
		return user;
	}

	@Override
	public PageInfo<User> listUser(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		UserExample userExample = new UserExample();
		List<User> users = this.userMapper
				.selectByExampleWithBLOBs(userExample);
		return new PageInfo<User>(users);
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return this.userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(User user) throws UserNotFoundException {
		int row = this.userMapper.deleteByPrimaryKey(user.getId());
		if (row == 0) {
			throw new UserNotFoundException("找不到该用户");
		}
	}

	@Override
	public User updateUserPic(HttpServletRequest request, User user,
			CommonsMultipartFile file) throws IllegalStateException,
			IOException {
		String fileName = user.getId()
				+ file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1);
		String savePath = request.getServletContext().getRealPath("/userPic")
				+ "\\" + fileName;
		File newFile = new File(savePath);
		file.transferTo(newFile);
		User u = new User();
		u.setId(user.getId());
		u.setUserPic(savePath);
		this.userMapper.updateByPrimaryKeySelective(u);
		user = this.userMapper.selectByPrimaryKey(u.getId());
		return user;
	}

}
