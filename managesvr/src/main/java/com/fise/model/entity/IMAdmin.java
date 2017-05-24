package com.fise.model.entity;

import java.util.List;

import com.fise.model.dto.UsertAuthCode;
import com.fise.utils.JsonUtil;

public class IMAdmin {
	private String UserName;
	private String AuthenticCode;
	private int AdminId;
	private int UserLevel;
	private String NickName;
	private String DepartId;
	private List<UsertAuthCode> usertAuthCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	
	
	
	public int getAdminId() {
		return AdminId;
	}
	public void setAdminId(int adminId) {
		AdminId = adminId;
	}
	public List<UsertAuthCode> getUsertAuthCode() {
		return usertAuthCode;
	}
	public void setUsertAuthCode(List<UsertAuthCode> usertAuthCode) {
		this.usertAuthCode = usertAuthCode;
	}
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
	public int getUserLevel() {
		return UserLevel;
	}
	public void setUserLevel(int userLevel) {
		UserLevel = userLevel;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getDepartId() {
		return DepartId;
	}
	public void setDepartId(String departId) {
		DepartId = departId;
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
	public IMAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IMAdmin(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
	
}
