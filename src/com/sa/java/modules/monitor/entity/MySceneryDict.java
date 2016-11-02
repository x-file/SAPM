package com.sa.java.modules.monitor.entity;

import java.io.Serializable;

public class MySceneryDict implements Serializable {

	private String sceneryId;
	private String sceneryName;
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}

	public MySceneryDict(){}
	public MySceneryDict(String sceneryId, String sceneryName){
		this.sceneryId = sceneryId;
		this.sceneryName = sceneryName;
	}
}
