package com.nights.retarded.sys.service;

public interface FeedbackService {

	/**
	 * 收到用户意见反馈
	 */
	void receiveFeedback(String openId, String content, String wechatId);

}