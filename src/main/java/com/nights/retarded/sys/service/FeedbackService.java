package com.nights.retarded.sys.service;

public interface FeedbackService {

	/**
	 * 收到用户意见反馈
	 * @param openId
	 * @param content
	 * @param wechatId
	 */
	public void receiveFeedback(String openId, String content, String wechatId);

}