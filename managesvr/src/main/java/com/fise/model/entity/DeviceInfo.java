package com.fise.model.entity;

public class DeviceInfo {
	private String UserName;
	private String AuthenticCode;
	private int DeviceId;
	private String DeviceIME;
	private String DeviceXW;
	private int ProductName;
	private String PhoneNo;
	private int status;
	private int DepartId;
	
	
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
	public int getDepartId() {
		return DepartId;
	}
	public void setDepartId(int departId) {
		DepartId = departId;
	}
	public int getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(int deviceId) {
		DeviceId = deviceId;
	}
	public String getDeviceIME() {
		return DeviceIME;
	}
	public void setDeviceIME(String deviceIME) {
		DeviceIME = deviceIME;
	}
	public String getDeviceXW() {
		return DeviceXW;
	}
	public void setDeviceXW(String deviceXW) {
		DeviceXW = deviceXW;
	}
	public int getProductName() {
		return ProductName;
	}
	public void setProductName(int productName) {
		ProductName = productName;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
		
}
