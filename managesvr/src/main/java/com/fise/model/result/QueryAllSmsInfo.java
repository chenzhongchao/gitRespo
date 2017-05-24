package com.fise.model.result;

import java.util.List;


public class QueryAllSmsInfo {
	private String UserName;
	private String AuthenticCode;	
	private int Status=0;
	private String ErrorInfo="error cause";
	private List<SmsInfo> list;
	
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
	public List<SmsInfo> getList() {
		return list;
	}
	public void setList(List<SmsInfo> list) {
		this.list = list;
	}
	
}
