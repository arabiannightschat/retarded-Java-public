package com.nights.retarded.sys.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.nights.retarded.sys.service.UserService;
import com.nights.retarded.sys.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.HttpUtil;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.common.utils.RedisUtils;
import com.nights.retarded.sys.model.enums.WxUrl;
import com.nights.retarded.sys.service.LoginRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//若返回JSON，需要加ResponseBody注解
//使用RestController代替Controller和ResponseBody,需要引入spring-boot-starter-web模块
@RestController
@RequestMapping("api/login")
@Api(value="登录")
public class LoginController {
	
	@Autowired
	private LoginRecordService loginRecordService;

	@Autowired
    private UserService userService;
	
	/*
	 * wxOpenId = {"session_key":"oII\/RFa6E\/QMA9ulB2lzAQ==","openid":"opxLy5I39n4oZyPg_CUysPgemVec"}
	 * 这里要生成一个随机token(sessionId)，在内存数据库中当做key，把服务器返回的session_key 和openid，把sessionId传给小程序
	 * 每次打开小程序查看wx.checkSession，如果过期，那么带上已经缓存在本地的sessionid(如果有的话)，重新发起登录请求，
	 * 后台从code2session中拿到新的请求结果后，生成新的随机sessionid并入库redis，并且把老的sessionid移除（如果有的话）
	 */
	@ApiOperation(value="微信小程序登录")
	@RequestMapping(value = "wxLogin", method = RequestMethod.GET)
	public String wxLogin(String code, String oldSessionId) {
		Map<String,String> params = new HashMap<>();
		params.put("appid", "wx3c56e6bc3d72a4eb");
		params.put("secret", "49a3170e05a402ba3f2c27520551e8b0");
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		String wxOpenId = HttpUtil.get(WxUrl.codeToOpenId.getUrl(), params);
		
		String sessionId = "AZ" + UUID.randomUUID().toString().replaceAll("-", "");
		RedisUtils.set(sessionId, wxOpenId);
		RedisUtils.del(oldSessionId);
		String openId = JsonUtils.toOpenId(wxOpenId);
		
		// 返回是否第一次登录，且入库登录记录表
		boolean isFirstLogin = loginRecordService.isFirstLogin(openId);
		loginRecordService.addLoginRecord(openId);
		
		HashMap<String,Object> result = new HashMap<>();
		result.put("sessionId", sessionId);
		result.put("isFirstLogin", isFirstLogin);
		return JsonUtils.mapToJson(result);
	}
	
	
	
}
