package com.fise.service.role;

import java.util.List;

import com.fise.model.dto.RoleInfo;
import com.fise.model.param.UserRole;



public interface RoleManagerService {
	public void insert_RoleInfo(UserRole userRole);
	public void update_RoleInfo(UserRole userRole);
	public List<RoleInfo> queryRoleInfo();
	public void delete_RoleInfo(int RoleId);
}
