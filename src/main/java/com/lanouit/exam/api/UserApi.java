package com.lanouit.exam.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.PositionIllegalException;
import com.lanouit.exam.exception.UserNotFoundException;
import com.lanouit.exam.exception.UserSendRegisterMailException;
import com.lanouit.exam.exception.UsermailExistException;
import com.lanouit.exam.po.User;
import com.lanouit.exam.service.UserService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.validator.group.UserLoginGroup;
import com.lanouit.exam.validator.group.UserRegisterGroup;
import com.lanouit.exam.validator.group.UserUpdateGroup;
import com.lanouit.exam.validator.group.UserUpdatePasswordGroup;

@RestController
@RequestMapping("/api/user")
public class UserApi {

	@Resource
	UserService userService;

	/**
	 * 分页获取所有用户
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listUser(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = Constant.PAGE_SIZE_VALUE) Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageInfo<User> users = this.userService.listUser(pageNum, pageSize);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("total", users.getTotal());
		result.put("data", users.getList());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUserById(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.userService.getUser(id);
		// 找不到记录
		if (user == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", user);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveUser(
			@Validated({ UserRegisterGroup.class }) @RequestBody User user,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// 注册用户
		try {
			user = userService.register(user);
			result.put("statusCode", "201");
			result.put("msg", "CREATED");
			result.put("data", user);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.CREATED);
		} catch (UsermailExistException e) {
			result.put("statusCode", Constant.USERMAIL_EXIST);
			result.put("msg", "邮箱已存在");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.CONFLICT);
		} catch (UserSendRegisterMailException e) {
			result.put("statusCode", Constant.USER_SEND_REGISTER_EMAIL_ERROR);
			result.put("msg", "发送用户注册激活邮件异常");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(
			@Validated({ UserLoginGroup.class }) @RequestBody User user,
			BindingResult bindingResult, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		user = userService.login(user);
		// 账号密码不正确
		if (user == null) {
			result.put("statusCode", Constant.USER_LOGIN_FAIL);
			result.put("msg", "用户登录账号密码不匹配,登录失败");
		}
		// 登录成功
		else {
			session.setAttribute("user", user);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", user);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Integer id,
			@Validated({ UserUpdateGroup.class }) @RequestBody User user,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			user.setId(id);
			user = userService.update(user);
		} catch (PositionIllegalException e) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", user);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据主键删除用户
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> delete(
			@PathVariable("id") Integer id, BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = new User();
		user.setId(id);
		try {
			this.userService.delete(user);
			result.put("statusCode", "204");
			result.put("msg", "NO_CONTENT");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			result.put("statusCode", "404");
			result.put("msg", "not found");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> updatePassword(
			@Validated({ UserUpdatePasswordGroup.class }) @RequestBody User user,
			BindingResult bindingResult, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		User sessionUser = (User) session.getAttribute("user");
		user.setId(sessionUser.getId());
		user = userService.updatePassword(user);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", user);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 用户头像更换
	 * 
	 * @param session
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/updateUserPic", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> updateUserPic(
			HttpServletRequest request, HttpSession session,
			@RequestParam("userPicFile") CommonsMultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 上传文件为空
		if (file.isEmpty()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// 更新头像
		User user = (User) session.getAttribute("user");
		try {
			user = this.userService.updateUserPic(request, user, file);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", user);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.OK);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			result.put("statusCode", Constant.USER_UPDATE_USERPIC_ERROR);
			result.put("msg", "用户更换头像异常");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
