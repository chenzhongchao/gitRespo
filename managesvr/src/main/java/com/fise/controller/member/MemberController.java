package com.fise.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.Comment;
import com.fise.model.entity.Gym;
import com.fise.model.entity.Member;
import com.fise.model.entity.Suggestion;
import com.fise.model.param.AccountPasswdResetParam;
import com.fise.model.param.CommentAddParam;
import com.fise.model.param.FeedbackParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.model.param.MemberUpdateParam;
import com.fise.model.param.MobileChangeParam;
import com.fise.model.param.PasswdResetParam;
import com.fise.model.param.RegisterParam;
import com.fise.model.param.SmsFetchParam;
import com.fise.model.result.CommentResult;
import com.fise.model.result.GymNearbyResult;
import com.fise.model.result.RegisterResult;
import com.fise.service.account.IAccountService;
import com.fise.service.comment.ICommentService;
import com.fise.service.gym.IGymService;
import com.fise.service.member.IMemberService;
import com.fise.utils.Constants;
import com.fise.utils.OSSUtil;
import com.fise.utils.StringUtil;


@RestController
@RequestMapping("/member")
public class MemberController {
	@Resource
	private IMemberService memberService;
	
	@Resource 
	private IGymService gymService;
	
	@Resource
	private ICommentService commentService;
	
	@Resource
	private IAccountService accountService;
	
	@IgnoreAuth
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response register(@RequestBody @Valid RegisterParam param) {
		
		Response resp = new Response();
		
		try {
			resp = memberService.register(param.getMobile(), param.getPassword(), param.getVerifyCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_SYSTEM);
			return resp;
		}
		
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody @Valid LoginParam param) {
		Response resp = new Response();
		
		try {
			resp = memberService.login(param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_SYSTEM);
			return resp;
		}
		
		return resp;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid LogoutParam param, HttpServletRequest request) {
		Response resp = new Response();
				
		resp = memberService.logout(param, request);
		
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/smscode/fetch", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid SmsFetchParam param) {
		Response resp = new Response();
		
		resp = memberService.smsCodeFetch(param);
		
		return resp;
	}
	
	@RequestMapping(value = "/oss/sign", method = RequestMethod.POST)
	public Response genUploadUrl() {
		Response resp = new Response();
		
		resp = OSSUtil.genSign();
		
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/nearby/gym", method = RequestMethod.POST)
	public Response getGymNearBy(@RequestBody @Valid Page<Gym> param) {
		Response resp = new Response();
		
		resp = gymService.getGymNearby(param);
		List<?> data = (List<?>)resp.getData();
		List<GymNearbyResult> gymNearbyResultList = new ArrayList<GymNearbyResult>();
		for(Object gymNearbyResult : data) {
			if (gymNearbyResult instanceof GymNearbyResult) {
				gymNearbyResultList.add((GymNearbyResult) gymNearbyResult);
			}
		}
		
		Page<GymNearbyResult> page = new Page<GymNearbyResult>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setResult(gymNearbyResultList);
		resp.success(page);
				
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/detail/gym/{id}", method = RequestMethod.POST)
	public Response getGymDetail(@PathVariable String id) {
		Response resp = new Response();
		Integer gymId = Integer.parseInt(id);
		
		resp = gymService.getGymDetail(gymId);
				
		return resp;
	}
	
	// 提交评价
	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	public Response postComment(@RequestBody @Valid CommentAddParam param) {
		Response resp = new Response();
		resp = memberService.postComment(param);
				
		return resp;
	}
	
	// 评价列表
	@RequestMapping(value = "/comment/list", method = RequestMethod.POST)
	public Response getGymComment(@RequestBody @Valid Page<Comment> pageParam) {
		Response resp = new Response();
		resp = memberService.getGymCommentList(pageParam);
		
		List<?> data = (List<?>)resp.getData();
		List<CommentResult> commentResultList = new ArrayList<CommentResult>();
		for (Object commentResult : data) {
			if (commentResult instanceof CommentResult) {
				commentResultList.add((CommentResult) commentResult);
			}
		}
		
		Page<CommentResult> page = new Page<CommentResult>();
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setTotalCount(pageParam.getTotalCount());
		page.setResult(commentResultList);
		resp.setData(page);
				
		return resp;
	}
	
	// 意见反馈
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public Response postFeedback(@RequestBody @Valid FeedbackParam param) {
		Response resp = new Response();
			
		Integer memberId = HttpContext.getMemberId();
		String content = param.getContent();
			
		Suggestion suggestion = new Suggestion();
		suggestion.setSourceSystemId(Constants.SUGGUESTION_SOURCE_SYSTEM_MEMBER.shortValue());
		suggestion.setSuggestion(content);
		suggestion.setSourceUserId(memberId);
		suggestion.setState(Constants.SUGGUESTION_STATE_CHECKING);
			
		gymService.postFeedback(suggestion);
		resp.success();
			
		return resp;
	}
	
	// 修改登录密码
	@IgnoreAuth
	@RequestMapping(value = "/passwd/reset", method = RequestMethod.POST)
	public Response resetPasswd(@RequestBody @Valid PasswdResetParam param) {
		Response resp = new Response();
				
		Integer memberId = HttpContext.getMemberId();
		resp = memberService.resetPasswd(memberId, param);
				
		return resp;
	}
	
	// 设置/重置支付密码
	@RequestMapping(value = "/account/passwd/reset", method = RequestMethod.POST)
	public Response resetAccountPasswd(@RequestBody @Valid AccountPasswdResetParam param) {
		Response resp = new Response();
				
		Integer memberId = HttpContext.getMemberId();
		resp = accountService.resetMemberAccountPasswd(memberId, param);
				
		return resp;
	}
	
	// 更换手机号
	@RequestMapping(value = "/mobile/change", method = RequestMethod.POST)
	public Response changeMobile(@RequestBody @Valid MobileChangeParam param) {
		Response resp = new Response();
					
		Integer memberId = HttpContext.getMemberId();
		resp = memberService.changeMobile(memberId, param);
					
		return resp;
	}
	
	// 用户信息更新
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response updateMemberInfo(@RequestBody @Valid MemberUpdateParam param) {
		Response resp = new Response();
					
		Integer memberId = HttpContext.getMemberId();
		if (memberId.intValue() != param.getMemberId().intValue()) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		
		Member memberForUpdate = new Member();
		memberForUpdate.setMemberId(memberId);
		if (StringUtil.isNotEmpty(param.getAvatar())) memberForUpdate.setAvatar(param.getAvatar());
		if (StringUtil.isNotEmpty(param.getNickName())) memberForUpdate.setNickName(param.getNickName());
		if (param.getSex() != null) memberForUpdate.setSex(param.getSex());
		if (param.getBirthday() != null) memberForUpdate.setBirthday(param.getBirthday());
		if (param.getHeight() != null) memberForUpdate.setHeight(param.getHeight());
		if (param.getWeight() != null) memberForUpdate.setWeight(param.getWeight());
		
		memberService.updateMember(memberForUpdate);
		
		resp.success();
		return resp;
	}
	
	// 获取用户画像
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public Response getUserProfile() {
		Response resp = new Response();
					
		Integer memberId = HttpContext.getMemberId();
		resp = memberService.getUserProfile(memberId);

		return resp;
	}
	
	// 初始化
	@RequestMapping(value = "/system/init", method = RequestMethod.POST)
	public Response initApp(@RequestBody @Valid InitParam param) {
		Response resp = new Response();
					
		Integer memberId = HttpContext.getMemberId();
		resp = memberService.initApp(memberId, param);

		return resp;
	}
}
