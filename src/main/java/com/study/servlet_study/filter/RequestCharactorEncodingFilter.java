package com.study.servlet_study.filter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter("/*")
public class RequestCharactorEncodingFilter extends HttpFilter implements Filter {
       
   
    public RequestCharactorEncodingFilter() {
        super();
       
    }

	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		String[] methods = new String[] { "POST", "PUT" };
		
		if(Arrays.asList(methods).contains(httpServletRequest.getMethod().toUpperCase())) {		//toUpperCase() -대문자로
			httpServletRequest.setCharacterEncoding("utf-8");
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
