package com.lanouit.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return "index.html";
	}
	
}
