package com.sa.java.modules.monitor.entity;

import java.util.List;

public class MySceneryCam {

	private String sceneryId;
	private String sceneryName;
	private List<MyCam>camList;

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

	public List<MyCam> getCamList() {
		return camList;
	}

	public void setCamList(List<MyCam> camList) {
		this.camList = camList;
	}

	public MySceneryCam(){}
	public MySceneryCam(String sceneryId, String sceneryName, List<MyCam> camList){
		this.sceneryId = sceneryId;
		this.sceneryName = sceneryName;
		this.camList = camList;
	}
}
