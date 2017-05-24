package com.fise.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.dto.RegisterStatDto;
import com.fise.model.entity.Member;


public interface MemberMapper {
	// 注册用户
	public void insertMember(Member member);
	
	// 注册用户（使用typeHandler）
	public void insertMember2(Member member);
	
	// 更新用户信息
	public void updateMemberLastLogin(Member member);
	
	// 更新用户信息
	public void updateMember(Member member);
	
	// 通过memberId查询member
	public Member getMemberById(Integer memberId) ;
	
	// 通过手机号查询member
	public Member getMemberByMobile(String mobile);
	
	// 查询注册手机号记录数
	public Integer getMobileCount(String mobile);
	
	// 取用户列表
	public List<Member> selectMemberPage(@Param("page") Page<Member> page);
	
	// 取用户总数
	public Long selectTotalCount();
	
	// 取冻结用户数
	public Long selectFrozenCount();
	
	// 取某段时间内的活跃用户数
	public Long selectActiveCount(@Param("beginTime") Date beginTime);
	
	// 取每日注册用户数
	public List<RegisterStatDto> selectRegDaily();
	
	// 取每日注册用户数
	public List<RegisterStatDto> selectRegWeekly();
		
	// 取每日注册用户数
	public List<RegisterStatDto> selectRegMonthly();
	
	// 取时间区间内分时注册数
	public List<RegisterStatDto> selectRegHourlyPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 取时间区间内按天用户注册数
	public List<RegisterStatDto> getRegDailyStatPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 取分时注册区间汇总数
	public Integer selectRegTotalPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 根据姓名取Member(模糊)
	public List<Member> selectMemberListByName(String nickName);
	
	// 根据手机号取Member(模糊)
	public List<Member> selectMemberListByMobile(String mobile);
}
