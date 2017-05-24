package com.fise.model.dto;

import java.util.List;

import com.fise.model.entity.FiseDevice;


public class DeviceInfoList {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private int DeviceCount;
	private int DeviceNo;
	private List<FiseDevice> DeviceInfo;
	private List<DeviceTypeInfo> DeviceTypeInfo;
	
	
	public List<DeviceTypeInfo> getDeviceTypeInfo() {
		return DeviceTypeInfo;
	}
	public void setDeviceTypeInfo(List<DeviceTypeInfo> deviceTypeInfo) {
		DeviceTypeInfo = deviceTypeInfo;
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
	public int getDeviceCount() {
		return DeviceCount;
	}
	public void setDeviceCount(int deviceCount) {
		DeviceCount = deviceCount;
	}
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
	public int getDeviceNo() {
		return DeviceNo;
	}
	public void setDeviceNo(int deviceNo) {
		DeviceNo = deviceNo;
	}
	public List<FiseDevice> getDeviceInfo() {
		return DeviceInfo;
	}
	public void setDeviceInfo(List<FiseDevice> deviceInfo) {
		DeviceInfo = deviceInfo;
	}
	public DeviceInfoList(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	public DeviceInfoList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
