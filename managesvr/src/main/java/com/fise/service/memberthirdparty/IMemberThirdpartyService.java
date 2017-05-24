package com.fise.service.memberthirdparty;

import com.fise.base.Response;
import com.fise.model.entity.MemberThirdparty;
import com.fise.model.param.OAuthParam;

public interface IMemberThirdpartyService {
	// 新增第三方登录记录
	public void insertMemberThirdparty(MemberThirdparty memberThirdparty);
	
	// 查第三方登录记录
	public MemberThirdparty getMemberThirdparty(MemberThirdparty memberThirdparty); 
	
	// 更新第三方登录记录
	public void updateMemberThirdparty(MemberThirdparty memberThirdparty);
	
	// 第三方登录绑定
	public Response bindThirdparty(OAuthParam param);
}
