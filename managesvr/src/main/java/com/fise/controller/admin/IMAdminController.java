package com.fise.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.model.dto.UsertAuthCode;
import com.fise.model.entity.IMAdmin;
import com.fise.model.result.UpdatePwd;
import com.fise.service.admin.IMAdminService;
import com.fise.utils.StringUtil;


@RestController
public class IMAdminController {
	@Resource
	IMAdminService imAdminService;
	
	//用户登陆系统
	@RequestMapping("/Manage/Login")
	public IMAdmin userLogin(@RequestBody Param param){
		Map<String, String> map=new HashMap<String, String>();
		map.put("UserName", param.UserName);
		map.put("PassWord", StringUtil.md5(param.PassWord));
		IMAdmin imAdmin=imAdminService.user_login(map);
		return imAdmin;
	}
	
	
	@RequestMapping("/Manage/GetPrivateAuth")
	public IMAdmin getPrivateAuth(@RequestBody Param1 param1){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("AuthFid", param1.AuthFid);
		map.put("UserId", param1.UserId);
		List<UsertAuthCode> usertAuthCodes=imAdminService.getPrivateAuth(map);
		IMAdmin imAdmin=new IMAdmin(param1.UserName,param1.AuthenticCode);
		imAdmin.setUsertAuthCode(usertAuthCodes);
		return imAdmin;
	}
	
	//用户修改密码
	@ResponseBody
	@RequestMapping("Manage/ModifyPassword")
	public UpdatePwd updatePassword(@RequestBody Param2 param2){
		Map<String, String> map=new HashMap<String, String>();
		map.put("UserName", param2.UserName);
		map.put("AuthenticCode", param2.AuthenticCode);
		map.put("Oldpassword", param2.Oldpassword);
		map.put("Newpassword", StringUtil.md5(param2.Newpassword));
		imAdminService.update_pwd(map);
		
		UpdatePwd updatePwd=selectOneByName(param2.UserName);
		updatePwd.setErrorInfo("error cause");
		return updatePwd;
	}
	
	//用户修改昵称
	@ResponseBody
	@RequestMapping("/Manage/ModifyNickName")
	public UpdatePwd updateNickName(@RequestBody Param3 param3){
		Map<String, String> map=new HashMap<String, String>();
		map.put("UserName", param3.UserName);
		map.put("AuthenticCode", param3.AuthenticCode);
		map.put("NewNickName", param3.NewNickName);
		imAdminService.update_nickname(map);
		
		UpdatePwd updatePwd=selectOneByName(param3.UserName);	
		updatePwd.setErrorInfo("error cause");
		return updatePwd;
	}
	
	//通过UserName 查询用户
	@ResponseBody
	@RequestMapping("/Manage/selectOneByName")
	public UpdatePwd selectOneByName(String UserName){
		UpdatePwd updatePwd=imAdminService.selectOneByName(UserName);
		return updatePwd;
	}
}

class Param{
	public String UserName;
	public String PassWord;
}

class Param1{
	public String UserName;
	public String AuthenticCode;
	public int AuthFid;
	public int UserId;
}

class Param2{
	public String UserName;
	public String AuthenticCode;
	public String Oldpassword;
	public String Newpassword;
}

class Param3{
	public String UserName;
	public String AuthenticCode;
	public String NewNickName;
}