package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.eunms.WechatAuthStateEnum;
import com.imooc.o2o.service1.WechatAuthService;

public class WechatAuthServiceTest extends BaseTest{

	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegiter(){
		//新增一条微信账号
		//给微信账号插入用户信息，但不设置用户id
		//希望创建微信账号的时候自动创建用户信息
		//通过openId查找新增的wechatAuth
		WechatAuth wechatAuth=new WechatAuth();
		PersonInfo personInfo=new PersonInfo();
		String openId="abcdefgd";
		personInfo.setCreateTime(new Date());
		personInfo.setUserType(1);
		personInfo.setEnableStatus(1);
		personInfo.setName("第三个微信用户信息");
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		wechatAuth.setPersonInfo(personInfo);
		WechatAuthExecution wechatAuthExecution=wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wechatAuthExecution.getState());
		assertEquals(WechatAuthStateEnum.SUCCESS.getStateInfo(), wechatAuthExecution.getStateInfo());
		wechatAuth=wechatAuthService.getWechatAuthByOpenId(openId);
		System.out.println(wechatAuth.getPersonInfo().getName());
	}
}
