package com.nights.retarded.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.sys.model.User;
import com.nights.retarded.sys.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/sys/user")
@Api(value="用户")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@ApiOperation(value="追加用户信息")
	@RequestMapping(value = "addUserInfo", method = RequestMethod.GET)
	public void addUserInfo(HttpServletRequest request,String userInfo) {
		String openId = JsonUtils.requestToOpenId(request);
		userService.addUserInfo(openId,userInfo);
	}
	
}
