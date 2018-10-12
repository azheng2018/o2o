package com.imooc.o2o.service;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;


import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.LocalAuthExcution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.eunms.LocalAuthStateEnum;
import com.imooc.o2o.service1.LocalAuthService;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest{
@Autowired
private LocalAuthService localAuthService;

@Test
@Ignore
public void testABindLocalAuth(){
	PersonInfo personInfo=new PersonInfo();
	LocalAuth localAuth=new LocalAuth();
	personInfo.setUserId(6L);
	localAuth.setCreateTime(new Date());
	localAuth.setLastEditTime(new Date());
	localAuth.setPassword("testUsername2");
	localAuth.setUsername("123");
	localAuth.setPersonInfo(personInfo);
	//绑定账号
	LocalAuthExcution le=localAuthService.bindLocalAuth(localAuth);
	assertEquals(LocalAuthStateEnum.SUCCESS.getState(),le.getState());
	//通过userId找到新增的localAuth
	localAuth=localAuthService.getLocalAuthBuUserId(localAuth.getPersonInfo().getUserId());
	System.out.println(localAuth.getUsername());
	System.out.println(localAuth.getPassword());
}
@Test
public void testBModifyLocalAuth(){
	long userId=6L;
	String username="123";
	String password="testUsername2";
	String newPassword="123";
	LocalAuthExcution le=localAuthService.modifyLocalAuth(userId, username, password, newPassword);
	assertEquals(LocalAuthStateEnum.SUCCESS.getState(), le.getState());
	LocalAuth localAuth=localAuthService.getLocalAuthByUsernameAndPwd(username, newPassword);
	System.out.println(localAuth.getUsername());
	System.out.println(localAuth.getPassword());
}
	
}
