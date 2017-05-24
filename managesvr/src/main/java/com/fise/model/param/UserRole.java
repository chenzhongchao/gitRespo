package com.fise.model.param;

public class UserRole {
	private String UserName;
	private String AuthenticCode;
	private int RoleId;
	private String RoleName;
	private int RoleLevel;
	private String RoleDescribe;
	
	
	
	public int getRoleId() {
		return RoleId;
	}
	public void setRoleId(int roleId) {
		RoleId = roleId;
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
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public int getRoleLevel() {
		return RoleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		RoleLevel = roleLevel;
	}
	public String getRoleDescribe() {
		return RoleDescribe;
	}
	public void setRoleDescribe(String roleDescribe) {
		RoleDescribe = roleDescribe;
	}
	
	
}
