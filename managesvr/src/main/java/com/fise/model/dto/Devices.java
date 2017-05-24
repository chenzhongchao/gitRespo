package com.fise.model.dto;

public class Devices {
	private String CompanyName;
	private int ActiveDevice;
	private int UnActiveDevice;
	private int OnlineDevice;
	
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public int getOnlineDevice() {
		return OnlineDevice;
	}
	public void setOnlineDevice(int onlineDevice) {
		OnlineDevice = onlineDevice;
	}
	public int getActiveDevice() {
		return ActiveDevice;
	}
	public void setActiveDevice(int activeDevice) {
		ActiveDevice = activeDevice;
	}
	public int getUnActiveDevice() {
		return UnActiveDevice;
	}
	public void setUnActiveDevice(int unActiveDevice) {
		UnActiveDevice = unActiveDevice;
	}
	
	
}
