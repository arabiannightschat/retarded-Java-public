package com.nights.retarded.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.utils.MailUtil;
import com.nights.retarded.sys.dao.FeedbackDao;
import com.nights.retarded.sys.model.entity.Feedback;
import com.nights.retarded.sys.model.entity.User;
import com.nights.retarded.sys.service.FeedbackService;
import com.nights.retarded.sys.service.UserService;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{

	@Resource(name = "feedbackDao")
	private FeedbackDao feedbackDao;
	
	@Resource(name = "userService")
	private UserService userService;

	@Override
	public void receiveFeedback(String openId, String content, String wechatId) {
		// 反馈记录入库
		Feedback feedback = new Feedback();
		feedback.setOpenId(openId);
		feedback.setContent(content);
		feedback.setWechatId(wechatId);
		this.feedbackDao.save(feedback);
		// 发送邮件给开发者
		User user = userService.findByOpenId(openId);
		StringBuffer sb = new StringBuffer();
		sb.append("	<div style=\"background: #eee;width: 100%;height: 100%;padding: 0;margin: 0;font-family:Microsoft YaHei;font-size: 15px;\">	");
		sb.append("		<div style=\"padding:30px\">	");
		sb.append("			<span style=\"color: #333;\">阿帐收到了一条信息反馈，如果您不知情，请忽略此邮件。</span>	");
		sb.append("			<br>	");
		sb.append("			<img src=\"" + user.getAvatarUrl() + "\" style=\"width: 40px;height:40px;margin-top: 20px;\">	");
		sb.append("			<div style=\"margin-top: 10px\">" + user.getNickName() + (wechatId != null ? "（微信号：" + wechatId + "）" : "") + "</div>	");
		sb.append("			<div style=\"font-size: 20px;font-weight: bold;margin-top: 20px\">" + content + "</div>	");
		sb.append("			<div style=\"margin-top: 20px;\">阿帐-记账帮手</div>	");
		sb.append("		</div>	");
		sb.append("	</div>	");
		try {
			MailUtil.sendEmails("1065531346@qq.com,873226608@qq.com","阿帐-记账帮手开发者：一个新的反馈信息",sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}