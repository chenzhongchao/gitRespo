package com.fise.controller.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.result.QueryAllSmsInfo;
import com.fise.model.result.SmsInfo;
import com.fise.model.result.UpdatePwd;
import com.fise.service.sms.IMSmsPlatService;
import com.fise.utils.JsonUtil;




@Controller
public class IMSmsPlatController {
	@Resource
	IMSmsPlatService imSmsPlatService;
	
	@ResponseBody
	@RequestMapping("/SmsPlatfrom/AddSmsInfo")
	public UpdatePwd insertUser(@RequestBody String json){
		SmsInfo smsInfo=JsonUtil.fromJson(json, SmsInfo.class);
		imSmsPlatService.insert_user(smsInfo);
		UpdatePwd updatePwd=new UpdatePwd(smsInfo.getUserName(),smsInfo.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/SmsPlatfrom/DelSmsInfo")
	public UpdatePwd deleteUser(@RequestBody Paran param){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("UserName", param.UserName);
		map.put("AuthenticCode",param.AuthenticCode);
		map.put("SmsPlatFromId",param.SmsPlatFromId);
		imSmsPlatService.delete_user(map);
		UpdatePwd updatePwd=new UpdatePwd(map.get("UserName").toString(),map.get("AuthenticCode").toString());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/SmsPlatfrom/ModifySmsInfo")
	public UpdatePwd updateUser(@RequestBody String json){
		SmsInfo smsInfo=JsonUtil.fromJson(json, SmsInfo.class);
		imSmsPlatService.update_user(smsInfo);
		UpdatePwd updatePwd=new UpdatePwd(smsInfo.getUserName(),smsInfo.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/SmsPlatfrom/GetSmsInfo")
	public QueryAllSmsInfo findAllSmsInfo(@RequestBody Param3 param){
		QueryAllSmsInfo queryAllSmsInfo=new QueryAllSmsInfo();
		queryAllSmsInfo.setUserName(param.UserName);
		queryAllSmsInfo.setAuthenticCode(param.AuthenticCode);
		queryAllSmsInfo.setList(imSmsPlatService.queryAllSmsPlat());	
		return queryAllSmsInfo;
	}
}

class Paran{
	String UserName;
	String AuthenticCode;
	int SmsPlatFromId;
}

class Param3{
	public String UserName;
	public String AuthenticCode;
	public String NewNickName;
}
