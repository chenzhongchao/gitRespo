package com.fise.model.param;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-9
 * @desc 第三方登录参数对象
 */

public class OAuthParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("third_party")
	private String thirdParty;		// wechat/qq/weibo
	
	private String code;			// web端登录的授权code
	
	@JsonProperty("login_type")
	private String loginType;		// app/web
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("open_id")
	private String openId;

	private String uid;				// 微博only
	
	@JsonProperty("member_id")
	private Integer memberId;
	
	private String mobile;
	
	@JsonProperty("sms_code")
	private String smsCode;
	
	@JsonProperty("user_info")
	private Map<String, String> userInfo;
	
	public String getThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public Map<String, String> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Map<String, String> userInfo) {
		this.userInfo = userInfo;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
