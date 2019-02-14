package com.chen.infoapp.util;

import com.chen.infoapp.identity.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	//private Logger logger = Logger.getLogger(LoginInterceptor.class);
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/** 拦截到用户的请求了 */
		String requestUrl = request.getRequestURL().toString();
        /** */
		//System.out.println("requestUrl:"+requestUrl);
		/** 判断session是否存在用户,如果存在说明用户已经登录了,应该放行 */
		User user = (User) request.getSession().getAttribute(OaContants.USER_SESSION);
		if(user!=null){
			//System.out.println("requestUrl:"+requestUrl+"->被放行！");
			/** 当前请求：每个请求是否都是一个线程   */
			UserHolder.addCurrentUser(user);
			return true;
		}else{
			// 重定向 
			response.sendRedirect(request.getContextPath()+"/oa/login");
			//System.out.println("requestUrl:"+requestUrl+"->被拦截！");
			return false;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserHolder.removeCurrentUser();
	}


	
    
	
}
