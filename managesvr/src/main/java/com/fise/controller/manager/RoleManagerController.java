package com.fise.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.dto.RoleInfo;
import com.fise.model.param.UserRole;
import com.fise.model.result.AllRoleInfo;
import com.fise.model.result.UpdatePwd;
import com.fise.service.role.RoleManagerService;
import com.fise.utils.JsonUtil;




@Controller
public class RoleManagerController {
	
	@Resource
	RoleManagerService roleManagerService;
	
	@ResponseBody
	@RequestMapping("/RoleManage/AddRoleInfo")
	public UpdatePwd addRoleInfo(@RequestBody String json){
		UserRole userRole=JsonUtil.fromJson(json, UserRole.class);
		roleManagerService.insert_RoleInfo(userRole);
		UpdatePwd updatePwd=new UpdatePwd(userRole.getUserName(),userRole.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/RoleManage/ModifyRoleInfo")
	public UpdatePwd modifyRoleInfo(@RequestBody String json){
		UserRole userRole=JsonUtil.fromJson(json, UserRole.class);
		roleManagerService.update_RoleInfo(userRole);
		UpdatePwd updatePwd=new UpdatePwd(userRole.getUserName(),userRole.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/RoleManage/GetRoleInfo")
	public AllRoleInfo getAllRoleInfo(@RequestBody Paran3 param){
		List<RoleInfo> list=roleManagerService.queryRoleInfo();
		AllRoleInfo allRoleInfo=new AllRoleInfo(param.UserName,param.AuthenticCode);
		allRoleInfo.setRoleInfos(list);
		return allRoleInfo;
	}
	
	@ResponseBody
	@RequestMapping("/RoleManage/DelRoleInfo")
	public UpdatePwd deleteRoleInfo(@RequestBody Paran3 param){
		roleManagerService.delete_RoleInfo(param.RoleId);
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		return updatePwd;
	}
}

class Paran3{
	public String UserName;
	public String AuthenticCode;
	public int RoleId;
}

