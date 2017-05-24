package com.fise.service.member;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.RegisterStatDto;
import com.fise.model.entity.Comment;
import com.fise.model.entity.Member;
import com.fise.model.param.CommentAddParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.model.param.MobileChangeParam;
import com.fise.model.param.PasswdResetParam;
import com.fise.model.param.SmsFetchParam;

public interface IMemberService {
	// 查询MemberId是否存在
	public boolean isMemberExist(Integer memberId);
	
	// 校验短信验证码是否正确
	public boolean checkSMSCode(String mobile, String code);
	
	// 查询手机号是否存在
	public boolean isMobileExist(String mobile);
	
	// 通过memberId查找Member实体
	public Member getMemberById(Integer memberId);
	
	// 通过手机号查Member实体
	public Member getMemberByMobile(String mobile);
	
	// 更新member表记录(作废)
	public void updateMemberLastLogin(Member member);
	
	// 注册
	public Response register(String mobile, String password, String verifyCode);
	
	// 登录
	public Response login(LoginParam param);
	
	// 退出
	public Response logout(LogoutParam param,HttpServletRequest request);
	
	// 获取短信验证码
	public Response smsCodeFetch(SmsFetchParam param);
	
	// 发表评论
	public Response postComment(CommentAddParam param);
	
	// 获取商户评论列表
	public Response getGymCommentList(Page<Comment> pageParam);

	// 第三方登录
	public Response thirdpartyLogin(Integer memberId);
	
	// 新增member表记录
	public void insertMember(Member member);
	
	// 更新member
	public void updateMember(Member member);
	
	// 修改密码
	public Response resetPasswd(Integer memberId, PasswdResetParam param);
	
	// 修改手机号
	public Response changeMobile(Integer memberId, MobileChangeParam param);
	
	// 获取用户画像
	public Response getUserProfile(Integer memberId);
	
	// app初始化接口
	public Response initApp(Integer memberId, InitParam param);
	
	// 取用户总数
	public Long getMemberTotalCount();
	
	// 取冻结用户数
	public Long getMemberFrozenCount();
	
	// 取某某日期内的活跃用户数
	public Long getMemberActiveCount(Date beginTime);
	
	// 取每日注册用户数
	public List<RegisterStatDto> getRegDailyStat();

	// 取每周注册用户数
	public List<RegisterStatDto> getRegWeeklyStat();
	
	// 取每月注册用户数
	public List<RegisterStatDto> getRegMonthlyStat(); 
	
	// 取时间区间内分时注册用户数
	public List<RegisterStatDto> getRegHourlyStatPeriod(Date startTime, Date endTime);
	
	// 取时间区间内按天注册用户数
	public List<RegisterStatDto> getRegDailyStatPeriod(Date startTime, Date endTime);

	// 取时间区间内注册用户数
	public Integer getRegTotalPeriod(Date startTime, Date endTime);
	
	// 通过姓名查找member(模糊)
	public List<Member> getMemberListByName(String name);
	
	// 通过手机号查找member(模糊)
	public List<Member> getMemberListByMobile(String mobile);
}
