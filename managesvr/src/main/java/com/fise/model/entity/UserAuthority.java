package com.fise.model.entity;

public class UserAuthority {
	private int UserId;
	private String UserName;
	private String AuthenticCode;
	private int AuthenticId;
	private String AuthorityName;
	private int AuthorityLevel;
	private int AuthRole;
	private int FatherId;
	private String AuthInterface;
	private String AuthDescrible;
	
	
	
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
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
	
	public int getAuthenticId() {
		return AuthenticId;
	}
	public void setAuthenticId(int authenticId) {
		AuthenticId = authenticId;
	}
	public String getAuthorityName() {
		return AuthorityName;
	}
	public void setAuthorityName(String authorityName) {
		AuthorityName = authorityName;
	}
	public int getAuthorityLevel() {
		return AuthorityLevel;
	}
	public void setAuthorityLevel(int authorityLevel) {
		AuthorityLevel = authorityLevel;
	}
	public int getAuthRole() {
		return AuthRole;
	}
	public void setAuthRole(int authRole) {
		AuthRole = authRole;
	}
	public int getFatherId() {
		return FatherId;
	}
	public void setFatherId(int fatherId) {
		FatherId = fatherId;
	}
	public String getAuthInterface() {
		return AuthInterface;
	}
	public void setAuthInterface(String authInterface) {
		AuthInterface = authInterface;
	}
	public String getAuthDescrible() {
		return AuthDescrible;
	}
	public void setAuthDescrible(String authDescrible) {
		AuthDescrible = authDescrible;
	}
	
	
}
