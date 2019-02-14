package com.chen.infoapp.util;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 登录拦截器 
 */
@SuppressWarnings("unchecked")
public class PopedomInterceptor extends HandlerInterceptorAdapter {
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		
		/** 拦截到用户的请求了 */
		String requestUrl = request.getRequestURI();
		//System.out.println("权限拦截器:---->requestUrl:"+requestUrl+"-->"+request.getProtocol());
		/**  判断是否是异步请求,权限不拦截所有的异步请求
		 *   x-requested-with---->XMLHttpRequest
		 * */
		if(request.getHeader("x-requested-with")!=null ||
				requestUrl.contains("show")||requestUrl.contains("preUser")){
			 return true ;
		}
		
		/** 判断当前用户请求的地址是否在权限session如果在就放行,如果不在就说明
		 * 用户没有此权限,需要被拦截 */
		Map<String ,List<String>> userPopedomOperasUrls = (Map<String, List<String>>) request.getSession().getAttribute(OaContants.USER_ALL_OPERAS_POPEDOM_URLS);
		for(Entry<String, List<String>> entry : userPopedomOperasUrls.entrySet()){
			String moduleUrl = entry.getKey() ;
			List<String> moduleOperasUrls = entry.getValue();
			if(requestUrl.contains(moduleUrl)){
				// 点击了某个模块或者某个模块的查询动态
				// 把当前模块对应的所有操作权限存入到session中 
				// 用于控制当前模块对应的界面展示哪些按钮  
				request.getSession().setAttribute(OaContants.MODULE_OPERAS_POPEDOM_URLS, moduleOperasUrls);
			}
		
			/** 判断当前用户的请求地址是否在权限session的所有操作地址中 */
			for(String operaUrl : moduleOperasUrls){
				if(requestUrl.contains(operaUrl)){
					return true ;
				}
			}
		}
		
		/** 没有权限 */
		request.setAttribute("tip", "对不起,您本次操作没有权限,请联系管理员开通权限!");
		request.getRequestDispatcher("/error.jsp").forward(request, response);
		return false ;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
