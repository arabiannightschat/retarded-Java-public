package com.nights.retarded.sys.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.sys.service.FeedbackService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/sys/feedback")
public class FeedbackController {

	@Resource(name="feedbackService")
	private FeedbackService feedbackService;

	@ApiOperation(value="用户使用反馈")
	@RequestMapping(value = "receiveFeedback", method = RequestMethod.GET)
	public void receiveFeedback(HttpServletRequest request,String content,String wechatId) {
		String openId = JsonUtils.requestToOpenId(request);
		feedbackService.receiveFeedback(openId,content,wechatId);
	}
}