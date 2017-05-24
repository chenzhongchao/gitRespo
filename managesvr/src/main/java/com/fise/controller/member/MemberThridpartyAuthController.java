package com.fise.controller.member;

import java.util.Date;
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
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.Comment;
import com.fise.model.entity.Gym;
import com.fise.model.entity.MemberThirdparty;
import com.fise.model.param.CommentAddParam;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.model.param.OAuthParam;
import com.fise.model.param.RegisterParam;
import com.fise.model.param.SmsFetchParam;
import com.fise.model.result.CommentResult;
import com.fise.model.result.GymNearbyResult;
import com.fise.model.result.RegisterResult;
import com.fise.service.comment.ICommentService;
import com.fise.service.gym.IGymService;
import com.fise.service.member.IMemberService;
import com.fise.service.memberthirdparty.IMemberThirdpartyService;
import com.fise.utils.Constants;
import com.fise.utils.JsonUtil;
import com.fise.utils.OSSUtil;
import com.fise.utils.StringUtil;
import com.fise.utils.oauth.wechat.WechatLoginUtils;
import com.qq.jutil.j4log.Logger;


@RestController
@RequestMapping("/member/thirdparty")
public class MemberThridpartyAuthController {
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IMemberThirdpartyService memberThirdpartyService;
	
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody @Valid OAuthParam param) {
		Logger logger = Logger.getLogger("thirdparty_login");
		Response resp = new Response();
		
		if (Constants.THIRD_PARTY_LOGIN_WECHAT.equals(param.getThirdParty())) {
			resp = WechatLoginUtils.requestUserInfo(param);
			logger.debug("after wx uesrinfo:" + JsonUtil.toJson(resp));
			
			if (resp.getCode() == ErrorCode.ERROR_OK.getCode()) {
				Map<String, String> userInfo = (Map<String, String>)resp.getData();
				
				MemberThirdparty memberThirdparty = new MemberThirdparty();
				memberThirdparty.setThirdpartyId(Integer.parseInt(userInfo.get("thirdparty_id")));
				memberThirdparty.setUnionid(userInfo.get("unionid"));
				
				MemberThirdparty memberThirdpartyInDb = memberThirdpartyService.getMemberThirdparty(memberThirdparty);
				if (memberThirdpartyInDb == null || memberThirdpartyInDb.getMemberId() == 0 ) {
					// 记录不包涵memberId的数据, 绑定需要验证
					if (memberThirdpartyInDb == null) {
						memberThirdpartyService.insertMemberThirdparty(memberThirdparty);
					} else if (memberThirdpartyInDb.getMemberId() == 0) {
						memberThirdpartyInDb.setUpdateTime(new Date());
						memberThirdpartyService.updateMemberThirdparty(memberThirdpartyInDb);
					}
					resp.failure(ErrorCode.ERROR_WECHAT_LOGIN_NOT_BIND);
					resp.setData(userInfo);
					return resp;
				}
				// 开始登录
				resp = memberService.thirdpartyLogin(memberThirdpartyInDb.getMemberId());
			}
		}
		
		return resp;
	}
	
	@IgnoreAuth
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public Response bindThirdparty(@RequestBody @Valid OAuthParam param) {
		Response resp = new Response();
		
		resp = memberThirdpartyService.bindThirdparty(param);
		
		return resp;
	}
}
