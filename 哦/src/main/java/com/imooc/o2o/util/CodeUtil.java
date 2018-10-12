//package com.imooc.o2o.util;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class CodeUtil {
//public static boolean checkVerifyCode(HttpServletRequest request){
//	String verifyCodeExpected=(String)request.getSession().getAttribute(
//			com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//	String verifyCodeActual=HttpServiletRequestUtil.getString(request, "verifyCodeActual");
//	if (verifyCodeExpected!=null&&verifyCodeExpected.equals(verifyCodeActual)) {
//		return true;
//	}else {
//		return false;
//	}
//	
//}
//}
package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	/**
	 * 检查验证码是否和预期相符
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
