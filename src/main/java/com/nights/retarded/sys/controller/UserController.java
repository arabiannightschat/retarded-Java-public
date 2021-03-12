package com.nights.retarded.sys.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.sys.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/sys/user")
public class UserController extends BaseController{
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(value = "addUserInfo", method = RequestMethod.POST)
	public void addUserInfo(String userInfo) {
		userService.addUserInfo(getOpenId(), userInfo);
	}
	
}
