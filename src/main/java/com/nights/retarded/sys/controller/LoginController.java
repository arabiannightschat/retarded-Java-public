package com.nights.retarded.sys.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import com.nights.retarded.utils.HttpUtils;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.utils.RedisUtils;
import com.nights.retarded.sys.model.enums.WxUrl;
import com.nights.retarded.sys.service.LoginRecordService;
import com.nights.retarded.sys.service.UserService;
import com.nights.retarded.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/sys/login")
public class LoginController extends BaseController {
	
	@Autowired
	private LoginRecordService loginRecordService;

	@Autowired
    private UserService userService;

	/**
     * 微信小程序登录接口
	 * wxOpenId = {"session_key":"oII\/RFa6E\/QMA9ulB2lzAQ==","openid":"opxLy5I39n4oZyPg_CUysPgemVec"}
	 */
	@RequestMapping(value = "wxLogin", method = RequestMethod.GET)
	public Result wxLogin(String code, String oldSessionId) {

	    String sessionId;
        String wxOpenId = RedisUtils.get(oldSessionId);
        String openId = JsonUtils.toOpenId(RedisUtils.get(oldSessionId));
	    if(StringUtils.isEmpty(wxOpenId) || StringUtils.isEmpty(openId)){

            Map<String,String> params = new HashMap<>();
            params.put("appid", "wx3c56e6bc3d72a4eb");
            params.put("secret", "49a3170e05a402ba3f2c27520551e8b0");
            params.put("js_code", code);
            params.put("grant_type", "authorization_code");
            wxOpenId = HttpUtils.getBody(WxUrl.codeToOpenId.getUrl(), params);

            sessionId = "azLoginSession:" + StringUtils.getUUID();
            RedisUtils.set(sessionId, wxOpenId, 30, TimeUnit.DAYS);
            RedisUtils.delete(oldSessionId);
            openId = JsonUtils.toOpenId(wxOpenId);

            // 如果是第一次登录
            if(userService.findByOpenId(openId) == null){
                // 创建用户
                userService.createUser(openId);
            } else {
                userService.countLoginInfo(openId);
            }

        } else {
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
