package com.imooc.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.LocalAuthExcution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.eunms.LocalAuthStateEnum;
import com.imooc.o2o.service1.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.MD5;

@Controller
@RequestMapping(value="local",method={RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
@Autowired
private LocalAuthService localAuthService;

/**
 * 将用户信息与平台账号绑定
 * @param request
 * @return
 */
@RequestMapping(value="/bindlocalauth",method=RequestMethod.POST)
@ResponseBody
private Map<String, Object> bindLocalAuth(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	// 验证码校验
			if (!CodeUtil.checkVerifyCode(request)) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "输入了错误的验证码");
				return modelMap;
			}
	//获取输入的账号
	String userName=HttpServletRequestUtil.getString(request, "userName");
	//获取输入的密码
	String password=HttpServletRequestUtil.getString(request, "password");
	//从seesion中获取当前用户信息(用户一旦通过微信登录，便能获取到用户信息user)
	PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
	//非空判断，要求账号密码以及当前的user不能为空
	if (user!=null&&user.getUserId()!=null&&userName!=null&&password!=null) {
		//创建localAuth对象并赋值
		LocalAuth localAuth=new LocalAuth();
		localAuth.setUsername(userName);
		localAuth.setPassword(password);
		localAuth.setPersonInfo(user);
		//绑定账号
		LocalAuthExcution le=localAuthService.bindLocalAuth(localAuth);
		if (le.getState()==LocalAuthStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("localAuth", localAuth);
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", le.getStateInfo());
		}
		
	}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "账号密码不能为空");
	}
	return modelMap;
}

/**
 * 修改密码
 * @param request
 * @return
 */
@RequestMapping(value="/changelocalpwd",method=RequestMethod.POST)
@ResponseBody
private Map<String, Object> changeLocalPwd(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	// 验证码校验
			if (!CodeUtil.checkVerifyCode(request)) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "输入了错误的验证码");
				return modelMap;
			}
	//获取账号
	String userName=HttpServletRequestUtil.getString(request, "userName");
	//获取原密码
	String password=HttpServletRequestUtil.getString(request, "password");
	//获取新密码
	String newPassword=HttpServletRequestUtil.getString(request, "newPassword");
	//从session获取当前用户信息
	PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
	//非空判断，要求新旧密码与当前用户的session不为空，且新就密码不同
	if (userName!=null&&password!=null&&newPassword!=null&&user!=null&&user.getUserId()!=null&&
			!password.equals(newPassword)) {
		try {//查看原先账号，看看与输入的账号是否一致，不一致则为非法操作
			LocalAuth localAuth=localAuthService.getLocalAuthBuUserId(user.getUserId());
			if (localAuth==null||!userName.equals(localAuth.getUsername())) {
				//不一样直接退出
				modelMap.put("success", false);
				modelMap.put("errMsg", "输入账号非登录账号");
				return modelMap;	
			}
			if (!MD5.getMD5(password).equals(localAuth.getPassword())) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "密码输入错误");
				return modelMap;
			}
			//修改平台账号的用户密码
			LocalAuthExcution le=localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
		if (le.getState()==LocalAuthStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", le.getStateInfo());
		}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
	}else {
		modelMap.put("success", false);
		modelMap.put("errMsg", "请输入修改信息");
	}
	return modelMap;
}
@RequestMapping(value="/logincheck",method=RequestMethod.POST)
@ResponseBody
private Map<String, Object> loginCheck(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	//获取是否需要进行验证码校验的标识符
	boolean needVerify=HttpServletRequestUtil.getBoolean(request, "needVerify");
	if (needVerify&&!CodeUtil.checkVerifyCode(request)) {
	modelMap.put("success", true);
	modelMap.put("errMsg", "输入验证码有误");
	return modelMap;	
	}
	//获取输入的账号
	String userName=HttpServletRequestUtil.getString(request, "userName");
	//获取输入的密码
	String password=HttpServletRequestUtil.getString(request, "password");
	//非空校验
	if (userName!=null&&password!=null) {
		LocalAuth localAuth=localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
		if (localAuth!=null) {
			//若能取得账号则登录成功
			modelMap.put("success", true);
			//同时在session中设置用户信息
			request.getSession().setAttribute("user", localAuth.getPersonInfo());
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入用户名或密码有误");
		}
	}else {
		modelMap.put("success", false);
		modelMap.put("errMsg", "输入用户名或密码为空");
	}
	return modelMap;
}


@RequestMapping(value="/logout",method=RequestMethod.POST)
@ResponseBody
/**
 * 用户点击登出按钮的时候注销session
 * @param request
 * @return
 */
private Map<String, Object> logout(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	//将用户session设置为空
	request.getSession().setAttribute("user", null);
	modelMap.put("success", true);
	return modelMap;
}
}
