package com.fise.model.result;

import java.util.List;

public class QueryBackStageInfos {
	private String UserName;
	private String AuthenticCode;	
	private int Status=0;
	private String ErrorInfo="error cause";
	private int BackStageInfoNo;
	private List<StageConf> list;
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
	public int getBackStageInfoNo() {
		return BackStageInfoNo;
	}
	public void setBackStageInfoNo(int backStageInfoNo) {
		BackStageInfoNo = backStageInfoNo;
	}
	public List<StageConf> getList() {
		return list;
	}
	public void setList(List<StageConf> list) {
		this.list = list;
	}
	
	
}
