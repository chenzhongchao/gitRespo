package com.fise.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.result.QueryBackStageInfos;
import com.fise.model.result.StageConf;
import com.fise.model.result.UpdatePwd;
import com.fise.service.system.IMSystemConfService;
import com.fise.utils.JsonUtil;




@Controller
public class IMSystemConfController {
	
	@Resource
	IMSystemConfService imSystemConfService;
	
	@ResponseBody
	@RequestMapping("/BackStageManage/AddConfInfo")
	public UpdatePwd addConfInfo(@RequestBody String json){
		StageConf stageConf=JsonUtil.fromJson(json, StageConf.class);
		imSystemConfService.insert_backstageconf(stageConf);
		UpdatePwd updatePwd=new UpdatePwd(stageConf.getUserName(),stageConf.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/BackStageManage/DelConfInfo")
	public UpdatePwd deleteConfInfo(@RequestBody Paran2 param){
		imSystemConfService.delete_backstageconf(param.BackStageId);
		UpdatePwd updatePwd=new UpdatePwd(param.UserName,param.AuthenticCode);
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/BackStageManage/QueryConfInfo")
	public QueryBackStageInfos QueryConfInfo(@RequestBody Paran1 param){
		QueryBackStageInfos queryBackStageInfos=new QueryBackStageInfos();
		queryBackStageInfos.setUserName(param.UserName);
		queryBackStageInfos.setAuthenticCode(param.AuthenticCode);
		List<StageConf> list=imSystemConfService.findbackstageinfos(param.BackStageConfName);
		queryBackStageInfos.setBackStageInfoNo(list.size());
		queryBackStageInfos.setList(list);
		return queryBackStageInfos;
	}
	
	@ResponseBody
	@RequestMapping("/BackStageManage/ModifyConfInfo")
	public UpdatePwd updateBackstageconf(@RequestBody String json){
		StageConf stageConf=JsonUtil.fromJson(json, StageConf.class);
		imSystemConfService.update_backstageconf(stageConf);
		UpdatePwd updatePwd=new UpdatePwd(stageConf.getUserName(),stageConf.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/BackStageManage/GetConfInfo")
	public QueryBackStageInfos QueryallConfInfo(@RequestBody Param3 param){
		QueryBackStageInfos queryBackStageInfos=new QueryBackStageInfos();
		queryBackStageInfos.setUserName(param.UserName);
		queryBackStageInfos.setAuthenticCode(param.AuthenticCode);
		List<StageConf> list=imSystemConfService.findallbackstageinfos();
		queryBackStageInfos.setBackStageInfoNo(list.size());
		queryBackStageInfos.setList(list);
		return queryBackStageInfos;
	}
}

class Paran1{
	public String UserName;
	public String AuthenticCode;
	public String BackStageConfName;
}

class Paran2{
	public String UserName;
	public String AuthenticCode;
	public int BackStageId;
}

class Param3{
	public String UserName;
	public String AuthenticCode;
	public String NewNickName;
}

