package com.fise.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.dto.ClientPlaceCount;
import com.fise.model.dto.ClientTypeCount;
import com.fise.model.dto.Devices;
import com.fise.model.dto.Register;
import com.fise.model.dto.XWInfo;
import com.fise.model.param.RegisterTime;
import com.fise.model.result.ClientCountInfo;
import com.fise.model.result.DeviceCountInfo;
import com.fise.model.result.RegisterCount;
import com.fise.service.data.DataManagerService;
import com.fise.utils.JsonUtil;



@Controller
public class DataManagerController {
	
	@Resource
	DataManagerService dataManagerService;
	
	@ResponseBody
	@RequestMapping("/DateCountManage/GetDeviceCountInfo")
	public DeviceCountInfo getDeviceCountInfo(@RequestBody Param_1 param){
		DeviceCountInfo deviceCountInfo=new DeviceCountInfo(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=2){
			deviceCountInfo.setErrorInfo("权限不够");
			return deviceCountInfo;
		}
		Devices device=dataManagerService.selectActiveDeviceCount(param.DepartId);
		XWInfo xwInfo=dataManagerService.selectXWCount(param.DepartId);
		deviceCountInfo.setDeviceInfo(device);
		deviceCountInfo.setXWInfo(xwInfo);
		return deviceCountInfo;
	}
	
	@ResponseBody
	@RequestMapping("/DateCountManage/GetClientCountInfo")
	public ClientCountInfo getClientCountInfo(@RequestBody Param_2 param){
		ClientCountInfo clientCountInfo=new ClientCountInfo(param.UserName,param.AuthenticCode);
		if(param.RoleLevel!=2){
			clientCountInfo.setErrorInfo("权限不够");
			return clientCountInfo;
		}
		int count=dataManagerService.findAllCount(param.DepartId);
		List<ClientTypeCount> ClientTypeCount=dataManagerService.findTypeCount(param.DepartId);
		int i=dataManagerService.findAllCount(param.DepartId);
		List<ClientPlaceCount> ClientPlaceCount=dataManagerService.findPlaceCount(param.DepartId);
		for(ClientTypeCount c:ClientTypeCount){
			if(c.getTypeName().equals("1")){
				c.setTypeName("win");
			}
			if(c.getTypeName().equals("2")){
				c.setTypeName("mac");
			}
			if(c.getTypeName().equals("17")){
				c.setTypeName("IOS");
			}
			if(c.getTypeName().equals("18")){
				c.setTypeName("android");
			}
			c.setRegisterCount(c.getRegisterCount()/i);
		}
		for(ClientPlaceCount c:ClientPlaceCount){
			c.setRegisterCount(c.getRegisterCount()/i);
		}
		clientCountInfo.setClientTypeCount(ClientTypeCount);
		clientCountInfo.setClientPlaceCount(ClientPlaceCount);
		return clientCountInfo;
	}
	
	@ResponseBody
	@RequestMapping("/DateCountManage/GetRegisterCountInfo")
	public RegisterCount getRegisterCountInfo(@RequestBody String json){
		RegisterTime registerTime=JsonUtil.fromJson(json, RegisterTime.class);
		RegisterCount registerCount=new RegisterCount(registerTime.getUserName(),registerTime.getAuthenticCode());
		if(registerTime.getRoleLevel()!=2){
			registerCount.setErrorInfo("你没有该操作权限");
			return registerCount;
		}
		List<Register> list=dataManagerService.getRegisterCount(registerTime);
		for(Register r:list){
			int s=(r.getRegisterCount()/dataManagerService.findAllCount(registerTime.getDepartId()))*100;
			r.setRateOfIncrease(s);
		}
		registerCount.setDeviceInfo(list);
		return registerCount;
	}
}

class Param_1{
	public String UserName;
	public String AuthenticCode;
	public int RoleLevel;
	public int DepartId;
}

class Param_2{
	public String UserName;
	public String AuthenticCode;
	public int RoleLevel;
	public int DepartId;
}
