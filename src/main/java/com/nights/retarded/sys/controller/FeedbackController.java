package com.nights.retarded.sys.controller;

import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.sys.service.FeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/sys/feedback")
public class FeedbackController {

	@Resource(name="feedbackService")
	private FeedbackService feedbackService;

	@RequestMapping(value = "receiveFeedback", method = RequestMethod.GET)
	public void receiveFeedback(HttpServletRequest request,String content,String wechatId) {
		String openId = JsonUtils.requestToOpenId(request);
		feedbackService.receiveFeedback(openId,content,wechatId);
	}
}