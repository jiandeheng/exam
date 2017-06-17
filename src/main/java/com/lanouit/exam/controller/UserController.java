package com.lanouit.exam.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanouit.exam.exception.UserActivateException;
import com.lanouit.exam.exception.UsermailExistException;
import com.lanouit.exam.po.User;
import com.lanouit.exam.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Resource
	UserService userService;

	@RequestMapping("/activateUser")
	public String activateUser(User user, Long time, Model model) {
		user.setUserCreatetime(new Date(time));
		model.addAttribute("msg", "激活成功");
		model.addAttribute("text", "前往登录");
		model.addAttribute("url", "/exam/");
		try {
			user = userService.activateUser(user);
		} catch (UserActivateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "激活异常");
			model.addAttribute("text", "返回注册");
			model.addAttribute("url", "/exam/");
		} catch (UsermailExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "该邮箱已被占用");
			model.addAttribute("text", "返回注册");
			model.addAttribute("url", "/exam/");
		}
		return "user/activate.jsp";
	}
	
}
