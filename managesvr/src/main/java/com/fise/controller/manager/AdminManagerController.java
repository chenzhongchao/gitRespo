package com.fise.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.dto.AuthDirInfo;
import com.fise.model.entity.AdminInfo;
import com.fise.model.param.AddAdmin;
import com.fise.model.result.AdminAllAuth;
import com.fise.model.result.CorCompanyAuth;
import com.fise.model.result.GetAllAdminInfo;
import com.fise.model.result.UpdatePwd;
import com.fise.service.admin.AdminManagerService;
import com.fise.utils.JsonUtil;



@Controller
public class AdminManagerController {
	
	@Resource
	AdminManagerService adminManagerService;
	
	@ResponseBody
	@RequestMapping("/AdminManage/CreateAdmin")
	public UpdatePwd createAdmin(@RequestBody String json){
		AddAdmin addAmin=JsonUtil.fromJson(json, AddAdmin.class);
		adminManagerService.insert_Admin(addAmin);
		UpdatePwd updatePwd=new UpdatePwd(addAmin.getUserName(),addAmin.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/ModifyAdminInfo")
	public UpdatePwd modifyAdminInfo(@RequestBody String json){
		AddAdmin addAmin=JsonUtil.fromJson(json, AddAdmin.class);
		adminManagerService.update_Admin(addAmin);
		UpdatePwd updatePwd=new UpdatePwd(addAmin.getUserName(),addAmin.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/GetCompanyAuthInfo")
	public CorCompanyAuth getCompanyAuthInfo(@RequestBody Param5 param){
		CorCompanyAuth corCompanyAuth=new CorCompanyAuth(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=3){
			corCompanyAuth.setErrorInfo("你没有该操作权限");
			return corCompanyAuth;
		}
		
		List<AuthDirInfo> list=adminManagerService.getCompanyAuth(param.DepartId);
		corCompanyAuth.setAuthDirInfo(list);
		return corCompanyAuth;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/AuthAdminName")
	public UpdatePwd authAdminName(@RequestBody Param4 param){
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=3){
			updatePwd.setErrorInfo("你没有该操作权限");
			return updatePwd;
		}
		
		int admin=adminManagerService.queryAdmin(param.NewUserName);
		if(admin!=0){
			updatePwd.setErrorInfo("该账户用户名已存在");
		}else{
			updatePwd.setErrorInfo("该用户名可用");
		}
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/DeleteAdmin")
	public UpdatePwd deleteAdmin(@RequestBody Param6 param){
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=3){
			updatePwd.setErrorInfo("你没有该操作权限");
			return updatePwd;
		}
		adminManagerService.delete_Admin(param.AdminId);
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/GetAdminInfo")
	public GetAllAdminInfo getAdminInfo(@RequestBody Param7 param){
		GetAllAdminInfo getAllAdminInfo=new GetAllAdminInfo(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=3){
			getAllAdminInfo.setErrorInfo("你没有该操作权限");
			return getAllAdminInfo;
		}
		List<AdminInfo> list=adminManagerService.getAdminInfo();
		getAllAdminInfo.setAdminInfo(list);
		return getAllAdminInfo;
	}
	
	@ResponseBody
	@RequestMapping("/AdminManage/GetAdminAuthInfo")
	public AdminAllAuth getAdminAuthInfo(@RequestBody Param8 param){
		AdminAllAuth adminAllAuth=new AdminAllAuth(param.UserName,param.AuthenticCode);
		List<AuthDirInfo> list=adminManagerService.GetAdminAuthInfo(param.UserId);
		adminAllAuth.setAdminAuthInfo(list);
		return adminAllAuth;
	}
}

class Param5{
	public String UserName;
	public String AuthenticCode;
	public Integer AdminId;
	public int RoleLevel;
	public int DepartId;
}

class Param4{
	public String UserName;
	public String AuthenticCode;
	public int RoleLevel;
	public String NewUserName;
}

class Param6{
	public String UserName;
	public String AuthenticCode;
	public int RoleLevel;
	public int AdminId;
}

class Param7{
	public String UserName;
	public String AuthenticCode;
	public int RoleLevel;
	public int DepartId;
}

class Param8{
	public String UserName;
	public String AuthenticCode;
	public int UserId;
}

