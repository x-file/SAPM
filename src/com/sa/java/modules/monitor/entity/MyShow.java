package com.sa.java.modules.monitor.entity;

public class MyShow {

	private String showId;
	private String showName;
	private String showUrl;
	public String getShowId() {
		return showId;
	}
	public void setShowId(String showId) {
		this.showId = showId;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public MyShow(){}
	public MyShow(String showId, String showName, String showUrl){
		this.showId = showId;
		this.showName = showName;
		this.showUrl = showUrl;
	}
	
}
