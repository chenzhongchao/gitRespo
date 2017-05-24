package com.fise.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fise.base.HttpContext;
import com.fise.utils.Constants;
import com.qq.jutil.j4log.Logger;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-24
 * @desc 给HttpContext赋值
 */

public class HttpContextFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,	FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		
		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
			return ;
		}
		
		HttpContext.init(request, response);
		try {
			filterChain.doFilter(request, response);
		} finally {
			HttpContext.destroy();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
