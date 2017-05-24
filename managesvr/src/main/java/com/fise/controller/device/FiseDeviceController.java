package com.fise.controller.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.model.dto.DeviceInfoList;
import com.fise.model.dto.DeviceTypeInfo;
import com.fise.model.dto.Devices;
import com.fise.model.entity.DeviceInfo;
import com.fise.model.entity.FiseDevice;
import com.fise.model.result.DeviceCount;
import com.fise.model.result.ReturnDeviceInfo;
import com.fise.model.result.UpdatePwd;
import com.fise.service.device.FiseDeviceService;
import com.fise.utils.JsonUtil;




@RestController
public class FiseDeviceController {
	
	@Resource
	FiseDeviceService fiseDeviceService;
	

	@RequestMapping("/FiseDeviceManage/InsertInfo")
	public UpdatePwd addFiseDevice(@RequestBody String json){
		/*List<FiseDevice> list=new ArrayList<FiseDevice>();
		FiseDevice fise1=new FiseDevice("112","23",34,45);
		FiseDevice fise2=new FiseDevice("71","22",33,44);
		list.add(fise1);
		list.add(fise2);
		devicelist.setDeviceInfo(list);*/
		DeviceInfoList devicelist=JsonUtil.fromJson(json, DeviceInfoList.class);
		fiseDeviceService.insert_FiseDevices(devicelist.getDeviceInfo());
		UpdatePwd updatePwd=new UpdatePwd(devicelist.getUserName(),devicelist.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/QueryDeviceInfo")
	public ReturnDeviceInfo QueryDeviceInfo(@RequestBody Param_4 param){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("DeviceIME", param.DeviceIME);
		map.put("DeviceXW", param.DeviceXW);
		DeviceInfo deviceInfo=fiseDeviceService.query_deviceInfo(map);
		ReturnDeviceInfo returnDeviceInfo=new ReturnDeviceInfo(param.UserName,param.AuthenticCode);
		returnDeviceInfo.setDeviceInfo(deviceInfo);
		return returnDeviceInfo;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/DelDeviceInfo")
	public UpdatePwd DelDeviceInfo(@RequestBody Param_3 param){
		fiseDeviceService.delete_device(param.DeviceId);
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/ModifyDeviceInfo")
	public UpdatePwd ModifyDeviceInfo(@RequestBody String json){
		DeviceInfo deviceInfo=JsonUtil.fromJson(json,DeviceInfo.class);
		fiseDeviceService.update_deviceInfo(deviceInfo);
		UpdatePwd updatePwd=new UpdatePwd(deviceInfo.getUserName(),deviceInfo.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/GetDeviceInfo")
	public DeviceInfoList GetDeviceInfo(@RequestBody Param_5 param){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("DepartId", param.DepartId);
		map.put("Page", param.Page);
		map.put("PageSize", param.PageSize);
		List<FiseDevice> list=fiseDeviceService.findpartdeviceinfo(map);
		DeviceInfoList deviceInfoList=new DeviceInfoList(param.UserName,param.AuthenticCode);
		deviceInfoList.setDeviceCount(list.size());
		deviceInfoList.setDeviceInfo(list);
		return deviceInfoList;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/GetDeviceTypeInfo")
	public DeviceInfoList GetDeviceTypeInfo(@RequestBody Param_6 param){
		List<DeviceTypeInfo> list=fiseDeviceService.findAllDeviceInfo();
		DeviceInfoList deviceInfoList=new DeviceInfoList(param.UserName,param.AuthenticCode);
		deviceInfoList.setDeviceTypeInfo(list);
		return deviceInfoList;
	}
	
	@ResponseBody
	@RequestMapping("/FiseDeviceManage/GetCompanyDeviceInfo")
	public DeviceCount GetCompanyDeviceInfo(@RequestBody Param_5 param){
		DeviceCount deviceCount=new DeviceCount(param.UserName,param.AuthenticCode);
		Devices devices=fiseDeviceService.getCompanyDeviceInfo(param.DepartId);	
		List<Devices> list=new ArrayList<Devices>();
		list.add(devices);
		deviceCount.setDeviceInfo(list);
		return deviceCount;
	}
	
	@ResponseBody
	@RequestMapping("/DeviceCount/GetAllDeviceCountInfo")
	public DeviceCount GetAllDeviceCountInfo(@RequestBody Param5 param){
		if(param.RoleLevel!=3){
			DeviceCount deviceCount=new DeviceCount(param.UserName,param.AuthenticCode);
			deviceCount.setStatus(1);
			deviceCount.setErrorInfo("你没有权限！！！");
			return deviceCount;
		}
		DeviceCount deviceCount=new DeviceCount(param.UserName,param.AuthenticCode);
		List<Devices> list=fiseDeviceService.findAllDeviceCount();
		deviceCount.setDeviceInfo(list);
		return deviceCount;
	}
}

class Param_4{
	public String UserName;
	public String AuthenticCode;
	public String DeviceIME;
	public String DeviceXW;
	public int DepartId;
}

class Param_3{
	public String UserName;
	public String AuthenticCode;
	public int DeviceId;
}

class Param_5{
	public String UserName;
	public String AuthenticCode;
	public int DepartId;
	public int Page;
	public int PageSize;
}

class Param_6{
	public String UserName;
	public String AuthenticCode;
}

class Param5{
	public String UserName;
	public String AuthenticCode;
	public Integer AdminId;
	public int RoleLevel;
	public int DepartId;
}
