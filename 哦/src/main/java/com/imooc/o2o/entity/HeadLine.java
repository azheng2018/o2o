package com.imooc.o2o.entity;

import java.util.Date;
//头条
public class HeadLine {
private Long lineid;
private String lineNmae;
private String lineLink;
private String lineImg;
private Integer priority;//0可用，1不可用
private Integer enableStatus;
private Date creatTime;
private Date lastEditTime;
public Long getLineid() {
	return lineid;
}
public void setLineid(Long lineid) {
	this.lineid = lineid;
}
public String getLineNmae() {
	return lineNmae;
}
public void setLineNmae(String lineNmae) {
	this.lineNmae = lineNmae;
}
public String getLineLink() {
	return lineLink;
}
public void setLineLink(String lineLink) {
	this.lineLink = lineLink;
}
public String getLineImg() {
	return lineImg;
}
public void setLineImg(String lineImg) {
	this.lineImg = lineImg;
}
public Integer getPriority() {
	return priority;
}
public void setPriority(Integer priority) {
	this.priority = priority;
}
public Integer getEnableStatus() {
	return enableStatus;
}
public void setEnableStatus(Integer enableStatus) {
	this.enableStatus = enableStatus;
}
public Date getCreatTime() {
	return creatTime;
}
public void setCreatTime(Date creatTime) {
	this.creatTime = creatTime;
}
public Date getLastEditTime() {
	return lastEditTime;
}
public void setLastEditTime(Date lastEditTime) {
	this.lastEditTime = lastEditTime;
}

}
