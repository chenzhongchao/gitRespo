package com.fise.dao;

import java.util.List;


import com.fise.model.dto.AuthDirInfo;
import com.fise.model.entity.AdminInfo;
import com.fise.model.param.AddAdmin;



public interface AdminManagerMapper {
	public void insert_Admin(AddAdmin addAmin);
	
	public void update_Admin(AddAdmin addAmin);
	
	public List<AuthDirInfo> getCompanyAuth(int DepartId);
	
	public int queryAdmin(String NewUserName);
	
	public void delete_Admin(int AdminId);
	
	public List<AdminInfo> getAdminInfo();
	
	public List<AuthDirInfo> GetAdminAuthInfo(int UserId);
}
