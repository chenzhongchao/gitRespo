package com.fise.service.manager;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.entity.Member;
import com.fise.model.param.AppStatParam;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerPasswdResetParam;

public interface IManagerService {
	// 登录
	public Response login(ManagerLoginParam param);
	
	// 退出
	public Response logout(Integer managerId, String accessToken);
	
	// app统计数据(按天、周、月汇总)
	public Response getAppStat(AppStatParam param);
	
	// app
	public Response getAppStatDetail(AppStatParam param);
	
	// 充值密码
	public Response resetPasswd(ManagerPasswdResetParam param);
}
