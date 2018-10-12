package com.imooc.o2o.entity;

import java.util.Date;
//店铺
public class Shop {
private Long shopId;//店铺id
private String shopName;//名称
private String shopDesc;//描述
private String shopAddr;//地址
private String phone;//联系方式
private String shopImg;//图片
private String priority;//权重，前端排位展示
private Date createTime;
private Date lastEditTime;
private Integer enableStatus;//-1不可用，0审核中，1可用
private String advice;//系统管理员给店铺的提示
private Area area;//店铺区域
private PersonInfo owner;//店铺拥有者
private ShopCategory shopCategory;//类别id
public Long getShopId() {
	return shopId;
}
public void setShopId(Long shopId) {
	this.shopId = shopId;
}
public String getShopName() {
	return shopName;
}
public void setShopName(String shopName) {
	this.shopName = shopName;
}
public String getShopDesc() {
	return shopDesc;
}
public void setShopDesc(String shopDesc) {
	this.shopDesc = shopDesc;
}
public String getShopAddr() {
	return shopAddr;
}
public void setShopAddr(String shopAddr) {
	this.shopAddr = shopAddr;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getShopImg() {
	return shopImg;
}
public void setShopImg(String shopImg) {
	this.shopImg = shopImg;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public Date getLastEditTime() {
	return lastEditTime;
}
public void setLastEditTime(Date lastEditTime) {
	this.lastEditTime = lastEditTime;
}
public Integer getEnableStatus() {
	return enableStatus;
}
public void setEnableStatus(Integer enableStatus) {
	this.enableStatus = enableStatus;
}
public String getAdvice() {
	return advice;
}
public void setAdvice(String advice) {
	this.advice = advice;
}
public Area getArea() {
	return area;
}
public void setArea(Area area) {
	this.area = area;
}
public PersonInfo getOwner() {
	return owner;
}
public void setOwner(PersonInfo owner) {
	this.owner = owner;
}
public ShopCategory getShopCategory() {
	return shopCategory;
}
public void setShopCategory(ShopCategory shopCategory) {
	this.shopCategory = shopCategory;
}

}
