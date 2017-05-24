package com.fise.service.admin.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.AdminManagerMapper;
import com.fise.model.dto.AuthDirInfo;
import com.fise.model.entity.AdminInfo;
import com.fise.model.param.AddAdmin;
import com.fise.service.admin.AdminManagerService;


@Service
public class AdminManagerServiceImpl implements AdminManagerService{

	@Autowired
	AdminManagerMapper adminManagerDao;
	
	@Override
	public void insert_Admin(AddAdmin addAmin) {
		adminManagerDao.insert_Admin(addAmin);
	}

	@Override
	public void update_Admin(AddAdmin addAmin) {
		adminManagerDao.update_Admin(addAmin);
	}

	@Override
	public List<AuthDirInfo> getCompanyAuth(int DepartId) {
		return adminManagerDao.getCompanyAuth(DepartId);
	}

	@Override
	public int queryAdmin(String NewUserName) {
		return adminManagerDao.queryAdmin(NewUserName);
	}

	@Override
	public void delete_Admin(int AdminId) {
		adminManagerDao.delete_Admin(AdminId);
	}

	@Override
	public List<AdminInfo> getAdminInfo() {
		return adminManagerDao.getAdminInfo();
	}

	@Override
	public List<AuthDirInfo> GetAdminAuthInfo(int UserId) {
		return adminManagerDao.GetAdminAuthInfo(UserId);
	}

}
