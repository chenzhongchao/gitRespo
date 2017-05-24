package com.fise.controller.manager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.param.AppStatParam;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerLogoutParam;
import com.fise.model.param.ManagerPasswdResetParam;
import com.fise.service.manager.IManagerService;
import com.fise.utils.Constants;
import com.qq.jutil.j4log.Logger;

@RestController
@RequestMapping("/manage")
public class ManagerController {
	@Resource
	IManagerService managerService;
	
	// 登录
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody @Valid ManagerLoginParam param) {
		Response resp = new Response();
		
		resp = managerService.login(param);
		
		return resp;
	}
	
	// 退出
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid ManagerLogoutParam param, HttpServletRequest request) {
		Response resp = new Response();
		
		Integer managerId = param.getManagerId();
		String accessToken = request.getHeader(Constants.MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN);
		resp = managerService.logout(managerId, accessToken);
		
		return resp;
	}
	
	// app统计(按日、按周、按月)
	@RequestMapping(value = "/app/stat", method = RequestMethod.POST)
	public Response getAppStat(@RequestBody @Valid AppStatParam param) {
		Response resp = new Response();
		
		resp = managerService.getAppStat(param);
		
		return resp;
	}
	
	// app统计
	@RequestMapping(value = "/app/stat/detail", method = RequestMethod.POST)
	public Response getAppStatDetail(@RequestBody @Valid AppStatParam param) {
		Response resp = new Response();
		
		Logger logger = Logger.getLogger("app_stat");
		logger.debug("AppStatParam=" + param.toString());
		resp = managerService.getAppStatDetail(param);
		
		return resp;
	}
	
	// 修改密码
	@RequestMapping(value = "/passwd/reset", method = RequestMethod.POST)
	public Response resetPasswd(@RequestBody @Valid ManagerPasswdResetParam param) {
		Response resp = new Response();
		
		resp = managerService.resetPasswd(param);
		
		return resp;
	}
}
