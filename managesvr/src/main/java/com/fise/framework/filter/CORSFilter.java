package com.fise.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.qq.jutil.j4log.Logger;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-18
 * @desc 给HttpContext赋值
 */

public class CORSFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Logger logger = Logger.getLogger("cors_filter");
		logger.info(request.getContextPath() + request.getRequestURI() + " method=" + request.getMethod());
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, PATCH, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers","FIT-AccessToken,FIT-UA,FIT-GymAccessToken,FIT-ManagerAccessToken,UUID,GUID,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age", "1800");
		response.addHeader("Cache-Control","no-cache");

		filterChain.doFilter(request, response);
	}
}
