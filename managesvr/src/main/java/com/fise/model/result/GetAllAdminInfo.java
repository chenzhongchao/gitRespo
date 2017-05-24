package com.fise.model.result;

import java.util.List;
import com.fise.model.entity.AdminInfo;

public class GetAllAdminInfo {
	private String UserName;
	private String AuthenticCode; 
	private int Status=0; 
	private String ErrorInfo="error cause";
	private List<AdminInfo> AdminInfo;
	
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
	public List<AdminInfo> getAdminInfo() {
		return AdminInfo;
	}
	public void setAdminInfo(List<AdminInfo> adminInfo) {
		AdminInfo = adminInfo;
	}
	public GetAllAdminInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GetAllAdminInfo(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
	
		
}
