package com.fise.utils.oauth.wechat;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.framework.config.ConfigProperties;
import com.fise.model.param.OAuthParam;
import com.fise.utils.Constants;
import com.fise.utils.JsonUtil;
import com.qq.jutil.j4log.Logger;
import com.qq.jutil.string.StringUtil;

public class WechatLoginUtils {
	// 微信登录获取token的根地址
	private static String wechatReqTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	// 微信登录获取用户信息根地址
	private static String wechatReqUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
	
	//
	public static Response requestUserInfo(OAuthParam param) {
		Response resp = new Response();
		
		Logger logger = Logger.getLogger("wechat_login");
		
		Map<String, String> userInfo = new HashMap<String, String>();
		
		String appid = ConfigProperties.getValue("wechat_app_appid");
		String appsecret = ConfigProperties.getValue("wechat_app_appsecret");
		String code= param.getCode();
		
		String url = wechatReqTokenUrl + "?appid=" + appid + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
		Map<String, String> wechatRespMap = JsonUtil.json2Map(HttpRequestor(url));
		String accessToken = wechatRespMap.get("access_token");
		String openid = wechatRespMap.get("openid");
		if (accessToken == null || openid == null) {
			resp.failure(ErrorCode.ERROR_WECHAT_LOGIN_QUEST_TOKEN_ERROR);
			return resp;
		}
		logger.debug("req token url=" + url);
		logger.debug("resp token map=" + JsonUtil.toJson(wechatRespMap));
		
		url = wechatReqUserInfoUrl + "?access_token=" + accessToken + "&openid=" + openid;
		Map<String, String > wechatInfo = JsonUtil.json2Map(HttpRequestor(url));
		
		logger.debug("req userinfo url=" + url);
		logger.debug("userinfo:" + JsonUtil.toJson(wechatInfo));
		
		if (StringUtil.isEmpty(wechatInfo.get("unionid"))) {
			resp.failure(ErrorCode.ERROR_WECHAT_LOGIN_QUEST_USERINFO_ERROR);
			return resp;
		}
		userInfo.put("thirdparty", Constants.THIRD_PARTY_LOGIN_WECHAT);
		userInfo.put("thirdparty_id", Constants.THIRD_PARTY_LOGIN_ID_WECHAT + "");
		userInfo.put("openid", openid);
		userInfo.put("appid", appid);
		userInfo.put("nickname", wechatInfo.get("nickname"));
		userInfo.put("avatar", wechatInfo.get("headimgurl"));
		userInfo.put("unionid", wechatInfo.get("unionid"));
		resp.success(userInfo);
		
		return resp;
	} 
	
	
	
	// HTTP请求器
	private static String HttpRequestor(String url) {
		String response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			
			try{
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					response = EntityUtils.toString(entity,"UTF-8");
				}
			} finally {
				resp.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("微信登录请求错误：" + e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("微信登录关闭链接错误：" + e.getMessage());
			}
		}
		
		return response;
	}
}
