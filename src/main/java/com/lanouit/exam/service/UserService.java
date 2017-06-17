package com.lanouit.exam.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.PositionIllegalException;
import com.lanouit.exam.exception.UserActivateException;
import com.lanouit.exam.exception.UserNotFoundException;
import com.lanouit.exam.exception.UserSendRegisterMailException;
import com.lanouit.exam.exception.UsermailExistException;
import com.lanouit.exam.po.User;

public interface UserService {

	/**
	 * 登录
	 * 
	 * @param user
	 * @return 登陆后的用户对象
	 */
	public User login(User user);

	/**
	 * 注册
	 * 
	 * @param user
	 * @return 注册后的用户对象
	 */
	public User register(User user) throws UsermailExistException,
			UserSendRegisterMailException;

	/**
	 * 激活用户
	 * 
	 * @param user
	 * @return
	 */
	public User activateUser(User user) throws UserActivateException,
			UsermailExistException;

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	public User update(User user) throws PositionIllegalException;

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void delete(User user) throws UserNotFoundException;

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	public User updatePassword(User user);

	/**
	 * 分页获取所有用户
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> listUser(Integer pageNum, Integer pageSize);

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(Integer id);

	/**
	 * 跟换用户头像
	 * 
	 * @param request
	 * @param user
	 * @param file
	 * @return
	 */
	public User updateUserPic(HttpServletRequest request, User user,
			CommonsMultipartFile file) throws IllegalStateException,
			IOException;

}
