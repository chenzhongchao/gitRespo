package com.fise.model.result;

import java.util.List;

import com.fise.model.param.IMCompany;



public class Admin {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private int CompanyCount;
	private List<IMCompany> CompanyInfo;
	
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
	public int getCompanyCount() {
		return CompanyCount;
	}
	public void setCompanyCount(int companyCount) {
		CompanyCount = companyCount;
	}
	public List<IMCompany> getCompanyInfo() {
		return CompanyInfo;
	}
	public void setCompanyInfo(List<IMCompany> companyInfo) {
		CompanyInfo = companyInfo;
	}
	
	
}
