package com.fise.model.result;

import java.util.List;
import com.fise.model.dto.ClientTypeCount;
import com.fise.model.dto.ClientPlaceCount;

public class ClientCountInfo {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private List<ClientTypeCount> ClientTypeCount;
	private List<ClientPlaceCount> ClientPlaceCount;
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
	public List<ClientTypeCount> getClientTypeCount() {
		return ClientTypeCount;
	}
	public void setClientTypeCount(List<ClientTypeCount> clientTypeCount) {
		ClientTypeCount = clientTypeCount;
	}
	public List<ClientPlaceCount> getClientPlaceCount() {
		return ClientPlaceCount;
	}
	public void setClientPlaceCount(List<ClientPlaceCount> clientPlaceCount) {
		ClientPlaceCount = clientPlaceCount;
	}
	public ClientCountInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClientCountInfo(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
}
