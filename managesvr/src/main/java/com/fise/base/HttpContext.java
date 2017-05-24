package com.fise.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-24
 * @desc http请求的上下文
 */

public class HttpContext {
	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();
    
    // 业务变量
    private static ThreadLocal<Integer> memberIdHolder = new ThreadLocal<Integer>(); 
    private static ThreadLocal<Integer> gymIdHolder = new ThreadLocal<Integer>();
    private static ThreadLocal<Integer> managerIdHolder = new ThreadLocal<Integer>();
    private static ThreadLocal<String> versionNameHolder = new ThreadLocal<String>();
    private static ThreadLocal<String> platformHolder = new ThreadLocal<String>();
    
    
    // 初始化
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        requestHolder.set(request);
        responseHolder.set(response);
    }

    // Destroy
    public static void destroy() {
        requestHolder.remove();
        responseHolder.remove();
    }

    // 获取Request
    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    // 获取Response
    public static HttpServletResponse getResponse() {
        return responseHolder.get();
    }
    
    // 设置memberId
    public static void setMemberId(Integer memberId) {
    	memberIdHolder.set(memberId);
    }
    
    // 获取memberId
    public static Integer getMemberId(){
    	return memberIdHolder.get();
    }
    
    // 设置gymId
    public static void setGymId(Integer gymId) {
    	gymIdHolder.set(gymId);
    }
    
    // 获取gymId
    public static Integer getGymId(){
    	return gymIdHolder.get();
    }
    
    // 设置gymId
    public static void setManagerId(Integer managerId) {
    	managerIdHolder.set(managerId);
    }    
    // 获取gymId
    public static Integer getManagerId(){
    	return managerIdHolder.get();
    }

    // 设置versionName
    public static void setVersionName(String versionName) {
    	versionNameHolder.set(versionName);
    }    
    // 获取versionName
    public static String getVersionName(){
    	return versionNameHolder.get();
    }
    // 设置platform
    public static void setPlatform(String platform) {
    	platformHolder.set(platform);
    }    
    // 获取platform
    public static String getPlatform(){
    	return platformHolder.get();
    }
}
