package com.fise.model.dto;

public class UsertAuthCode {
	private String AuthDirName;
	private String AuthInterface;
	private int AuthDirId;
	
	public String getAuthDirName() {
		return AuthDirName;
	}
	public void setAuthDirName(String authDirName) {
		AuthDirName = authDirName;
	}
	public String getAuthInterface() {
		return AuthInterface;
	}
	public void setAuthInterface(String authInterface) {
		AuthInterface = authInterface;
	}
	public int getAuthDirId() {
		return AuthDirId;
	}
	public void setAuthDirId(int authDirId) {
		AuthDirId = authDirId;
	}

}
