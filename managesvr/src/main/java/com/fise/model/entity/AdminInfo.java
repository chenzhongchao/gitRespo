package com.fise.model.entity;

public class AdminInfo {
	private int AdminId;
	private int RoleLevel;
	private String UserName;
	private String UserPd;
	private String UserNick;
	private String UserCompany;
	private String LinkMan;
	private String LinkPhone;
	private String Email;
	public int getAdminId() {
		return AdminId;
	}
	public void setAdminId(int adminId) {
		AdminId = adminId;
	}
	public int getRoleLevel() {
		return RoleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		RoleLevel = roleLevel;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPd() {
		return UserPd;
	}
	public void setUserPd(String userPd) {
		UserPd = userPd;
	}
	public String getUserNick() {
		return UserNick;
	}
	public void setUserNick(String userNick) {
		UserNick = userNick;
	}
	public String getUserCompany() {
		return UserCompany;
	}
	public void setUserCompany(String userCompany) {
		UserCompany = userCompany;
	}
	public String getLinkMan() {
		return LinkMan;
	}
	public void setLinkMan(String linkMan) {
		LinkMan = linkMan;
	}
	public String getLinkPhone() {
		return LinkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		LinkPhone = linkPhone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
}
