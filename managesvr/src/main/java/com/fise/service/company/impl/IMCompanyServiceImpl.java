package com.fise.service.company.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.IMCompanyMapper;
import com.fise.model.param.IMCompany;
import com.fise.model.result.Admin;
import com.fise.service.company.IMCompanyService;



@Service
public class IMCompanyServiceImpl implements IMCompanyService{
	
	@Autowired
	IMCompanyMapper imCompanyDao;

	@Override
	public void insertCompany(IMCompany imCompany) {
		imCompanyDao.insertCompany(imCompany);	
	}

	@Override
	public Admin findcorporates(Map map) {
		return imCompanyDao.findcorporates(map);
	}

	@Override
	public Admin selectcorporates(Map map) {
		return imCompanyDao.selectcorporates(map);
	}

	@Override
	public void update_corporate(IMCompany imCompany) {
		imCompanyDao.update_corporate(imCompany);
		
	}

	@Override
	public void delete_corporate(Map map) {
		imCompanyDao.delete_corporate(map);
		
	}
	
	
}
