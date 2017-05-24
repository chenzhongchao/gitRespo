package com.fise.model.result;

import com.fise.model.dto.Devices;
import com.fise.model.dto.XWInfo;

public class DeviceCountInfo {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private Devices DeviceInfo;
	private XWInfo XWInfo;
	
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
	public Devices getDeviceInfo() {
		return DeviceInfo;
	}
	public void setDeviceInfo(Devices deviceInfo) {
		DeviceInfo = deviceInfo;
	}
	public XWInfo getXWInfo() {
		return XWInfo;
	}
	public void setXWInfo(XWInfo xWInfo) {
		XWInfo = xWInfo;
	}
	public DeviceCountInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeviceCountInfo(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
	
}
