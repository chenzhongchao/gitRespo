package com.fise.model.result;

import java.util.List;

import com.fise.model.dto.AuthDirInfo;

public class AdminAllAuth {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private List<AuthDirInfo> AdminAuthInfo;
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
	
	public List<AuthDirInfo> getAdminAuthInfo() {
		return AdminAuthInfo;
	}
	public void setAdminAuthInfo(List<AuthDirInfo> adminAuthInfo) {
		AdminAuthInfo = adminAuthInfo;
	}
	public AdminAllAuth() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminAllAuth(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
	
}
