package com.fise.dao;

import java.util.Map;

import com.fise.model.param.IMCompany;
import com.fise.model.result.Admin;



public interface IMCompanyMapper {
	public void insertCompany(IMCompany imCompany);
	
	public Admin findcorporates(Map map);
	
	public Admin selectcorporates(Map map);
	
	public void update_corporate(IMCompany imCompany);
	
	public void delete_corporate(Map map);
}
