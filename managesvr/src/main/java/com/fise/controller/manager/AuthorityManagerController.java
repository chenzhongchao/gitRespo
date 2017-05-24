package com.fise.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fise.model.entity.AuthorityInfo;
import com.fise.model.entity.UserAuthority;
import com.fise.model.result.AuthorityInfoList;
import com.fise.model.result.UpdatePwd;
import com.fise.service.user.UserAuthorityManagerService;
import com.fise.utils.JsonUtil;




@Controller
public class AuthorityManagerController {
	
	@Resource
	UserAuthorityManagerService userAuthorityManagerService;
	
	@ResponseBody
	@RequestMapping("/AuthorityManage/AddAuthority")
	public UpdatePwd addAuthority(@RequestBody String json){
		UserAuthority userAuthority=JsonUtil.fromJson(json, UserAuthority.class);
		userAuthorityManagerService.insert_authority(userAuthority);
		UpdatePwd updatePwd=new UpdatePwd(userAuthority.getUserName(),userAuthority.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AuthorityManage/ModifyAuthority")
	public UpdatePwd updateAuthority(@RequestBody String json){
		UserAuthority userAuthority=JsonUtil.fromJson(json, UserAuthority.class);
		userAuthorityManagerService.update_authority(userAuthority);
		UpdatePwd updatePwd=new UpdatePwd(userAuthority.getUserName(),userAuthority.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AuthorityManage/DelAuthority")
	public UpdatePwd deleteAuthority(@RequestBody String json){
		UserAuthority userAuthority=JsonUtil.fromJson(json, UserAuthority.class);
		userAuthorityManagerService.delete_authority(userAuthority);
		UpdatePwd updatePwd=new UpdatePwd(userAuthority.getUserName(),userAuthority.getAuthenticCode());
		return updatePwd;
	}
	
	@ResponseBody
	@RequestMapping("/AuthorityManage/GetAuthority")
	public AuthorityInfoList getAllAuthoritys(@RequestBody Param9 param){
		List<AuthorityInfo> list=userAuthorityManagerService.findAllAuthoritys(param.AuthRole);
		AuthorityInfoList authorityInfoList=new AuthorityInfoList(param.UserName, param.AuthenticCode);
		authorityInfoList.setAuthorityInfos(list);
		return authorityInfoList;
	}
}

class Param9{
	public String UserName;
	public String AuthenticCode;
	public int AuthRole;
	public int FatherId;
}

