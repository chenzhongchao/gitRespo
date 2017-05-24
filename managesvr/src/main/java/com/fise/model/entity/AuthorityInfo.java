package com.fise.model.entity;

public class AuthorityInfo {
	private int AuthorityId;
	private String AuthorityName;
	private int AuthorityLevel;
	private int AuthRole;
	private int FatherId;
	private String AuthInterface;
	private String AuthDescrible;
	
	public int getAuthorityId() {
		return AuthorityId;
	}
	public void setAuthorityId(int authorityId) {
		AuthorityId = authorityId;
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
