package com.imooc.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imooc.o2o.entity.PersonInfo;

/**
 * 店家管理系统登录验证拦截器
 * 
 * @author Administrator
 *
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 主要做事前拦截，即用户操作发生前，改变preHandler的逻辑，进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从session取出用户信息
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			// 若用户信息不为null，则将session的用户信息转换为personInfo实体类
			PersonInfo user = (PersonInfo) userObj;
			// 做空值判断，确保userId不为空且可用状态为1，并且店家类型为店家
			if (user != null && user.getUserId() != null && user.getEnableStatus() == 1 && user.getUserId() > 0) {
				// 若通过验证则返回true，拦截器返回true后，用户接下来的操作得以正常进行
				return true;
			}
		}
		// 若不满足登录验证，则直接跳转至登录验证页面
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;// 停止执行controller的方法

	}
}
