package com.fise.service.role.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.RoleManagerMapper;
import com.fise.model.dto.RoleInfo;
import com.fise.model.param.UserRole;
import com.fise.service.role.RoleManagerService;


@Service
public class RoleManagerServiceImpl implements RoleManagerService{
	
	@Autowired
	RoleManagerMapper roleManagerDao;
	
	@Override
	public void insert_RoleInfo(UserRole userRole) {
		roleManagerDao.insert_RoleInfo(userRole);
	}

	@Override
	public void update_RoleInfo(UserRole userRole) {
		roleManagerDao.update_RoleInfo(userRole);
	}

	@Override
	public List<RoleInfo> queryRoleInfo() {
		return roleManagerDao.queryRoleInfo();
	}

	@Override
	public void delete_RoleInfo(int RoleId) {
		roleManagerDao.delete_RoleInfo(RoleId);
	}

}
