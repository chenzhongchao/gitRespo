package com.fise.model.result;



public class SmsInfo {
	private String UserName;
	private String AuthenticCode;
	private int SmsPlatFromId;
	private String SmsPlatFromName;
	private int SmsPlatFromStatus;
	private String SmsPlatFromConf;
	
	
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
	public int getSmsPlatFromId() {
		return SmsPlatFromId;
	}
	public void setSmsPlatFromId(int smsPlatFromId) {
		SmsPlatFromId = smsPlatFromId;
	}
	public String getSmsPlatFromName() {
		return SmsPlatFromName;
	}
	public void setSmsPlatFromName(String smsPlatFromName) {
		SmsPlatFromName = smsPlatFromName;
	}
	public int getSmsPlatFromStatus() {
		return SmsPlatFromStatus;
	}
	public void setSmsPlatFromStatus(int smsPlatFromStatus) {
		SmsPlatFromStatus = smsPlatFromStatus;
	}
	public String getSmsPlatFromConf() {
		return SmsPlatFromConf;
	}
	public void setSmsPlatFromConf(String smsPlatFromConf) {
		SmsPlatFromConf = smsPlatFromConf;
	}
	
	
}
