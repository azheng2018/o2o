package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest{

	
@Autowired
private LocalAuthDao localAuthDao;
private static final String username="testUsername1";
private static final String password="testPassword";

@Test
@Ignore
public void testAInserLocalAuth(){
	PersonInfo personInfo=new PersonInfo();
	LocalAuth localAuth=new LocalAuth();
	personInfo.setUserId(3L);
	localAuth.setPersonInfo(personInfo);
	localAuth.setCreateTime(new Date());
	localAuth.setPassword(password);
	localAuth.setUsername(username);
	int effectNum=localAuthDao.insertLocalAuth(localAuth);
	assertEquals(1,effectNum);
}
@Test
@Ignore
public void testBQueryLocalByUsernameAndPwd(){
	LocalAuth localAuth=new LocalAuth();
	localAuth=localAuthDao.queryLocalByUserNameAndPwd(username, password);
	assertEquals("首个微信用户信息", localAuth.getPersonInfo().getName());
}
@Test
@Ignore
public void testCQueryLocalByUserId(){
	LocalAuth localAuth=new LocalAuth();
	localAuth=localAuthDao.queryLocalByUserId(3L);
	assertEquals("首个微信用户信息", localAuth.getPersonInfo().getName());
}
@Test
public void testDUpdateLocalAuth(){
	Date date=new Date();
	int effectNum=localAuthDao.updateLocalAuth(1L, username, password,"4565", date);
	assertEquals(1, effectNum);
	LocalAuth localAuth=localAuthDao.queryLocalByUserId(3L);
	System.out.println(localAuth.getPassword());
}
	
}
