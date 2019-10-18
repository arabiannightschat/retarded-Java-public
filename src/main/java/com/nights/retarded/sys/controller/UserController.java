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

//若返回JSON，需要加ResponseBody注解
//使用RestController代替Controller和ResponseBody,需要引入spring-boot-starter-web模块
@RestController
@RequestMapping("api/user")
@Api(value="用户")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@ApiOperation(value="获取用户UI喜好")
	@RequestMapping(value = "getUI", method = RequestMethod.GET)
	public String getUI(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);	
		User user = userService.findByOpenId(openId);
		return JsonUtils.objectToJson(user);
	}
	
	@ApiOperation(value="修改UI喜好")
	@RequestMapping(value = "recordUI", method = RequestMethod.GET)
	public void recordUI(HttpServletRequest request,String user) {
		String openId = JsonUtils.requestToOpenId(request);
		userService.recordUI(openId, user);
	}
	
	@ApiOperation(value="追加用户信息")
	@RequestMapping(value = "addUserInfo", method = RequestMethod.GET)
	public void addUserInfo(HttpServletRequest request,String userInfo) {
		String openId = JsonUtils.requestToOpenId(request);
		userService.addUserInfo(openId,userInfo);
	}
	
}
