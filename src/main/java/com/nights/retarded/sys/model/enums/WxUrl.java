package com.nights.retarded.sys.model.enums;

public enum WxUrl {
	
	codeToOpenId("https://api.weixin.qq.com/sns/jscode2session");
	
	private String url;
	
	public String getUrl() {
		return url;
	}

	private WxUrl(String url) {
		this.url = url;
	}
}
