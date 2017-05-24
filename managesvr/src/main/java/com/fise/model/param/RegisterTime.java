package com.fise.model.param;

public class RegisterTime {
	private String UserName;
	private String AuthenticCode;
	private String StartTime;
	private String EndTime;
	private int Days;
	private int RoleLevel;
	private int DepartId;
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
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public int getDays() {
		return Days;
	}
	public void setDays(int days) {
		Days = days;
	}
	public int getRoleLevel() {
		return RoleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		RoleLevel = roleLevel;
	}
	public int getDepartId() {
		return DepartId;
	}
	public void setDepartId(int departId) {
		DepartId = departId;
	}
	
	
}
