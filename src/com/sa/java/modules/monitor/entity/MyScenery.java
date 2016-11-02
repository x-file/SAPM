package com.sa.java.modules.monitor.entity;

import java.util.List;

public class MyScenery {

	private String sceneryId;
	private String sceneryName;
	private List<MyShow>showList;
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
	public List<MyShow> getShowList() {
		return showList;
	}
	public void setShowList(List<MyShow> showList) {
		this.showList = showList;
	}

	public MyScenery(){}
	public MyScenery(String sceneryId, String sceneryName, List<MyShow> showList){
		this.sceneryId = sceneryId;
		this.sceneryName = sceneryName;
		this.showList = showList;
	}
}
