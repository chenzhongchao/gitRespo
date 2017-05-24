package com.fise.service.manager;

import java.util.List;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.MemberDto;
import com.fise.model.entity.Member;
import com.fise.model.param.MemberAccountFrozenParam;
import com.fise.model.param.MemberUpdateParam;

public interface IManagerMemberService {
	// 设置用户状态
	public Response setMemberStatus(Integer memberId, Integer status);
	
	// 获取用户列表
	public List<MemberDto> getMemberList(Page<Member> pageParam);
	
	// 获取用户详情
	public MemberDto getMemberDetail(Integer memberId);
	
	// 更新用户信息
	public Response updateMember(MemberUpdateParam param);
	
	// 冻结用户部分金额
	public Response updateMemberAccountForFrozen(MemberAccountFrozenParam param);
}
