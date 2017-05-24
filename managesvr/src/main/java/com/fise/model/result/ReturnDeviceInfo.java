package com.fise.model.result;

import com.fise.model.entity.DeviceInfo;

public class ReturnDeviceInfo {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private DeviceInfo DeviceInfo;
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getAuthenticCode() {
		return AuthenticCode;
	}
	public void setAuthenticCode(String authenticCode) {
		AuthenticCode = authenticCode;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getErrorInfo() {
		return ErrorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}
	
	public DeviceInfo getDeviceInfo() {
		return DeviceInfo;
	}
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		DeviceInfo = deviceInfo;
	}
	public ReturnDeviceInfo(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	public ReturnDeviceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
