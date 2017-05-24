package com.fise.controller.company;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.model.param.IMCompany;
import com.fise.model.result.Admin;
import com.fise.model.result.UpdatePwd;
import com.fise.service.company.IMCompanyService;
import com.fise.utils.JsonUtil;




@RestController
public class IMCompanyController {
	@Resource
	IMCompanyService imCompanyService;
	
	@ResponseBody
	@RequestMapping("/CooperativeCompany/AddCompany")
	public UpdatePwd addCompany(@RequestBody String json){
		IMCompany imCompany=JsonUtil.fromJson(json, IMCompany.class);
		imCompanyService.insertCompany(imCompany);
		UpdatePwd updatepwd=null;
		updatepwd=new UpdatePwd(imCompany.getUserName(),imCompany.getAuthenticCode());
		return updatepwd;
	}
	
	@ResponseBody
	@RequestMapping("/CooperativeCompany/GetCompanyInfo")
	public Admin findcorporates(@RequestBody Param_5 param){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("UserName", param.UserName);
		map.put("AuthenticCode", param.AuthenticCode);
		map.put("Page", param.Page);
		map.put("PageSize", param.PageSize);
		Admin admin=imCompanyService.findcorporates(map);
		admin.setCompanyCount(admin.getCompanyInfo().size());
		return admin;
	}
	
	@ResponseBody
	@RequestMapping("/CooperativeCompany/QueryCompanyInfo")
	public Admin selectcorporates(@RequestBody Param_7 param){
		Admin admin=new Admin();
		if(param.RoleLevel!=3){
			admin.setErrorInfo("你没有该操作权限");
			return admin;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CompanyName", param.CompanyName);
		map.put("Page", param.Page);
		map.put("PageSize", param.PageSize);
		admin=imCompanyService.selectcorporates(map);
		admin.setUserName(param.UserName);
		admin.setAuthenticCode(param.AuthenticCode);
		admin.setCompanyCount(admin.getCompanyInfo().size());
		return admin;
	}
	
	@ResponseBody
	@RequestMapping("/CooperativeCompany/ModifyCompanyInfo")
	public UpdatePwd updateCorporate(@RequestBody String json){	
		IMCompany imCompany=JsonUtil.fromJson(json, IMCompany.class);
		imCompanyService.update_corporate(imCompany);
		UpdatePwd updatePwd=new UpdatePwd(imCompany.getUserName(),imCompany.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/CooperativeCompany/DelCompanyInfo")
	public UpdatePwd deleteCorporate(@RequestBody Param_8 param){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("UserName", param.UserName);
		map.put("AuthenticCode", param.AuthenticCode);
		map.put("CompanyId", param.CompanyId);
		imCompanyService.delete_corporate(map);
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		return updatePwd;
	}
}

class Param_5{
	public String UserName;
	public String AuthenticCode;
	public int DepartId;
	public int Page;
	public int PageSize;
}

class Param_7{
	public String UserName;
	public String AuthenticCode;
	public String CompanyName;
	public int RoleLevel;
	public int Page;
	public int PageSize;
}

class Param_8{
	public String UserName;
	public String AuthenticCode;
	public int CompanyId;
}
