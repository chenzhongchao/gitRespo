package com.fise.model.result;

public class UpdatePwd {
	private String UserName;
	private String AuthenticCode;	
	private int Status=0;
	private String ErrorInfo="error cause";
	
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
	
	public UpdatePwd(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	public UpdatePwd() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
