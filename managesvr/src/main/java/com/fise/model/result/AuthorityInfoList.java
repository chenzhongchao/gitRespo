package com.fise.model.result;

import java.util.List;

import com.fise.model.entity.AuthorityInfo;

public class AuthorityInfoList {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private List<AuthorityInfo> authorityInfos;
	
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
	public List<AuthorityInfo> getAuthorityInfos() {
		return authorityInfos;
	}
	public void setAuthorityInfos(List<AuthorityInfo> authorityInfos) {
		this.authorityInfos = authorityInfos;
	}
	public AuthorityInfoList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthorityInfoList(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
	
}
