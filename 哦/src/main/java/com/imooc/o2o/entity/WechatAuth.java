package com.imooc.o2o.entity;

import java.util.Date;
//微信账号
public class WechatAuth {
private Long wechatAuthId;
private String openId;
private Date createTime;
private PersonInfo personInfo;//与用户表关联的用户ID
public Long getWechatAuthId() {
	return wechatAuthId;
}
public void setWechatAuthId(Long wechatAuthId) {
	this.wechatAuthId = wechatAuthId;
}
public String getOpenId() {
	return openId;
}
public void setOpenId(String openID) {
	this.openId = openID;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date creatTime) {
	this.createTime = creatTime;
}
public PersonInfo getPersonInfo() {
	return personInfo;
}
public void setPersonInfo(PersonInfo personInfo) {
	this.personInfo = personInfo;
}

}
