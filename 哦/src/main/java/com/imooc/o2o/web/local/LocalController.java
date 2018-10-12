package com.imooc.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalController {

	/**
	 * 绑定账号页面路由
	 * @return
	 */
	@RequestMapping(value="/accountbind",method=RequestMethod.GET)
	public String accountbind(){
		return "local/accountbind";
	}
	/**
	 * 登录账号页面路由
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "local/login";
	}
	/**
	 * 修改密码页面路由
	 * @return
	 */
	@RequestMapping(value="/changepsw",method=RequestMethod.GET)
	public String changePsw(){
		return "local/changepsw";
	}

	
}
