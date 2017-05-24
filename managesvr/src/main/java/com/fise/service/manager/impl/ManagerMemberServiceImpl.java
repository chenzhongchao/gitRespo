package com.fise.service.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.GymImgsDescMapper;
import com.fise.dao.GymMapper;
import com.fise.dao.MemberMapper;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.model.dto.MemberDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymImgsDesc;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.MemberAccountTransaction;
import com.fise.model.param.GymAddParam;
import com.fise.model.param.MemberAccountFrozenParam;
import com.fise.model.param.MemberUpdateParam;
import com.fise.model.result.GymStatusResult;
import com.fise.model.result.MemberStatusResult;
import com.fise.service.account.IAccountService;
import com.fise.service.account.IAccountTransactionService;
import com.fise.service.manager.IManagerGymService;
import com.fise.service.manager.IManagerMemberService;
import com.fise.service.member.impl.MemberServiceImpl;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerMemberServiceImpl implements IManagerMemberService {
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IAccountTransactionService accountTransactionService;
	
	@Autowired
	private MemberMapper memberDao;
	
	@Override
	public Response setMemberStatus(Integer memberId, Integer status) {
		Response resp = new Response();
		
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setStatus(status);
			memberDao.updateMember(member);
			
			member = memberDao.getMemberById(memberId);
			MemberStatusResult result = new MemberStatusResult();
			result.setMemberId(memberId);
			result.setStatus(member.getStatus());
			
			resp.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_DATABASE);
		}
		
		return resp;
	}

	@Override
	public List<MemberDto> getMemberList(Page<Member> pageParam) {
		
		if (pageParam.getExtraParam() != null && StringUtil.isNotEmpty((String)pageParam.getExtraParam().get("query"))) {
			String query = (String)pageParam.getExtraParam().get("query");
			if (StringUtil.isNumber(query)) {
				pageParam.getExtraParam().put("mobile", query);
			} else {
				pageParam.getExtraParam().put("nick_name", query);
			}
		}
		
		List<Member> memberList = memberDao.selectMemberPage(pageParam);
		List<MemberDto> memberDtoList = new ArrayList<MemberDto>();
		for (Member member : memberList) {
			MemberDto memberDto = new MemberDto();
			MemberAccount account = accountService.getMemberAccountByMemberId(member.getMemberId());
			
			memberDto.setMemberId(member.getMemberId());
			memberDto.setMobile(member.getMobile());
			memberDto.setNickName(member.getNickName());
			memberDto.setSex(member.getSex());
			memberDto.setAvatar(member.getAvatar());
			memberDto.setBirthday(member.getBirthday());
			memberDto.setHeight(member.getHeight());
			memberDto.setWeight(member.getWeight());
			memberDto.setTel(member.getTel());
			memberDto.setStatus(member.getStatus());
			memberDto.setTotalBalance(account.getAvailableBalance() + account.getFrozenBalance());
			memberDto.setAvailableBalance(account.getAvailableBalance());
			memberDto.setFrozenBalance(account.getFrozenBalance());
			
			memberDtoList.add(memberDto);
		}

		return memberDtoList;
	}

	@Override
	public MemberDto getMemberDetail(Integer memberId) {
		Member member = memberDao.getMemberById(memberId);
		
		if (member == null) {
			return null;
		}

		MemberAccount account = accountService.getMemberAccountByMemberId(memberId);
		
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberId(member.getMemberId());
		memberDto.setMobile(member.getMobile());
		memberDto.setNickName(member.getNickName());
		memberDto.setSex(member.getSex());
		memberDto.setAvatar(member.getAvatar());
		memberDto.setBirthday(member.getBirthday());
		memberDto.setHeight(member.getHeight());
		memberDto.setWeight(member.getWeight());
		memberDto.setTel(member.getTel());
		memberDto.setStatus(member.getStatus());
		memberDto.setTotalBalance(account.getAvailableBalance() + account.getFrozenBalance());
		memberDto.setAvailableBalance(account.getAvailableBalance());
		memberDto.setFrozenBalance(account.getFrozenBalance());
		
		return memberDto;
	}

	@Override
	public Response updateMember(MemberUpdateParam param) {
		Response resp = new Response();
		if (param.getMemberId() == null) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		
		Member member = new Member();	
		member.setMemberId(param.getMemberId());
		if (param.getBirthday() != null) member.setBirthday(param.getBirthday());
		if (param.getNickName() != null) member.setNickName(param.getNickName());
		if (param.getSex() != null) member.setSex(param.getSex());
		if (param.getHeight() != null) member.setHeight(param.getHeight());
		if (param.getWeight() != null) member.setWeight(param.getWeight());
		memberDao.updateMember(member);
	    resp.success();
		
		return resp;
	}

	@Override
	public Response updateMemberAccountForFrozen(MemberAccountFrozenParam param) {
		Response resp = new Response();
		
		Integer memberId = param.getMemberId();
		Long frozenAmount = param.getFrozenAmount();
		
		Member member = memberDao.getMemberById(memberId);
		if (member == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		MemberAccount account = accountService.getMemberAccountByMemberId(param.getMemberId());
		if (account.getAvailableBalance() < frozenAmount) {
			resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_AVAILABLEBALANCE_LACK);
			return resp;
		}
		
		Long frozenBalance = account.getFrozenBalance();
		Long availableBalance = account.getAvailableBalance();
		Long preAvailableBalance = availableBalance;
		frozenBalance += frozenAmount;
		availableBalance -= frozenAmount;
		Long postAvailableBalance = availableBalance;
		
		MemberAccount accountForUpdate = new MemberAccount();
		accountForUpdate.setAccountId(account.getAccountId());
		accountForUpdate.setAvailableBalance(availableBalance);
		accountForUpdate.setFrozenBalance(frozenBalance);
		accountService.updateMemberAccountBalance(accountForUpdate);
		
		// 记录账户交易流水
		String transNo = CommonUtil.getTransactionNo(member);
		MemberAccountTransaction memberAccountTransaction = new MemberAccountTransaction(); 
		memberAccountTransaction.setTransNo(transNo);
		memberAccountTransaction.setMemberId(member.getMemberId());
		memberAccountTransaction.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_PARTIAL_ACCOUNT_FROZEN);
		memberAccountTransaction.setPreBalance(preAvailableBalance);
		memberAccountTransaction.setPostBalance(postAvailableBalance);
		memberAccountTransaction.setAmount(frozenAmount);
		memberAccountTransaction.setState(Constants.ACCOUNT_TRANSACTION_STATE_SYSTEM_FROZEN);
		accountTransactionService.insertMemberAccountTransaction(memberAccountTransaction);
		

		return resp;
	}
}
