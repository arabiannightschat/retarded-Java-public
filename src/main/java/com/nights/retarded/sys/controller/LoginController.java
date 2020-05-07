package com.nights.retarded.sys.controller;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.common.utils.HttpUtil;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.common.utils.RedisUtils;
import com.nights.retarded.sys.model.enums.WxUrl;
import com.nights.retarded.sys.service.LoginRecordService;
import com.nights.retarded.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/sys/login")
@Api(value="登录")
public class LoginController extends BaseController {
	
	@Autowired
	private LoginRecordService loginRecordService;

	@Autowired
    private UserService userService;

	/*
	 * wxOpenId = {"session_key":"oII\/RFa6E\/QMA9ulB2lzAQ==","openid":"opxLy5I39n4oZyPg_CUysPgemVec"}
	 *
	 */
	@ApiOperation(value="微信小程序登录")
	@RequestMapping(value = "wxLogin", method = RequestMethod.GET)
	public Map wxLogin(String code, String oldSessionId) {

	    String openId;
	    String sessionId;
	    if(StringUtils.isEmpty(RedisUtils.get(oldSessionId))){

            Map<String,String> params = new HashMap<>();
            params.put("appid", "wx3c56e6bc3d72a4eb");
            params.put("secret", "49a3170e05a402ba3f2c27520551e8b0");
            params.put("js_code", code);
            params.put("grant_type", "authorization_code");
            String wxOpenId = HttpUtil.get(WxUrl.codeToOpenId.getUrl(), params);

            sessionId = "AZ" + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
            RedisUtils.set30Days(sessionId, wxOpenId);
            RedisUtils.del(oldSessionId);
            openId = JsonUtils.toOpenId(wxOpenId);

            // 如果是第一次登录
            if(userService.findByOpenId(openId) == null){
                // 创建用户
                userService.createUser(openId);
            } else {
                userService.countLoginInfo(openId);
            }

        } else {
            openId = JsonUtils.toOpenId(RedisUtils.get(oldSessionId));
            userService.countLoginInfo(openId);
            sessionId = oldSessionId;
        }

		// 记录登录记录
		loginRecordService.addLoginRecord(openId);

		HashMap<String,Object> result = new HashMap<>();
		result.put("sessionId", sessionId);
		result.put("chargeDayCount",userService.findByOpenId(openId).getChargeDayCount());
		return Success(result);
	}
	
}
