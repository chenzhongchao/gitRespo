package com.fise.dao;

import java.util.List;

import com.fise.model.dto.RoleInfo;
import com.fise.model.param.UserRole;


public interface RoleManagerMapper {
	public void insert_RoleInfo(UserRole userRole);
	
	public void update_RoleInfo(UserRole userRole);
	
	public List<RoleInfo> queryRoleInfo();
	
	public void delete_RoleInfo(int RoleId);
}
