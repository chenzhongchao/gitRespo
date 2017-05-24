package com.fise.controller.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.MemberDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.Member;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerLogoutParam;
import com.fise.model.param.MemberAccountFrozenParam;
import com.fise.model.param.MemberStatusParam;
import com.fise.model.param.MemberUpdateParam;
import com.fise.service.manager.IManagerMemberService;
import com.fise.service.manager.IManagerService;
import com.fise.service.member.IMemberService;
import com.fise.utils.Constants;

@RestController
@RequestMapping("/manage/member")
public class ManagerMemberController {
	@Resource
	IManagerMemberService managerMemberService;
	
	// 冻结用户
	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public Response lock(@RequestBody @Valid MemberStatusParam param) {
		Response resp = new Response();
		
		Integer memberId = param.getMemberId();
		Integer status = 1;		// 0.启动   1.冻结
		resp = managerMemberService.setMemberStatus(memberId, status);
		
		return resp;
	}
	
	// 解冻用户
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	public Response unlock(@RequestBody @Valid MemberStatusParam param) {
		Response resp = new Response();
		
		Integer memberId = param.getMemberId();
		Integer status = 0;		// 0.启动   1.冻结
		resp = managerMemberService.setMemberStatus(memberId, status);
		
		return resp;
	}
	
	// 用户列表
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Response memberList(@RequestBody @Valid Page<Member> pageParam) {
		Response resp = new Response();
		
		List<MemberDto> memberDtoList = managerMemberService.getMemberList(pageParam);
		
		Page<MemberDto> page = new Page<MemberDto>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(memberDtoList);
		resp.success(page);
		
		return resp;
	}
	
	// 用户详情
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	public Response memberDetail(@PathVariable String id) {
		Response resp = new Response();
		
		Integer memberId = Integer.parseInt(id);
		MemberDto memberDto = managerMemberService.getMemberDetail(memberId);
		resp.success(memberDto);
		
		return resp;
	}
	
	// 更新用户信息
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response updateMember(@RequestBody @Valid MemberUpdateParam param) {
		Response resp = new Response();

		resp = managerMemberService.updateMember(param);
		
		return resp;
	}
	
	// 冻结用户部分资金
	@RequestMapping(value = "/account/frozen", method = RequestMethod.POST)
	public Response frozenMemberPartialAccount(@RequestBody @Valid MemberAccountFrozenParam param) {
		Response resp = new Response();
		
		resp = managerMemberService.updateMemberAccountForFrozen(param);
		
		return resp;
	}
}
