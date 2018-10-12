package com.imooc.o2o.entity;

import java.util.Date;
//区域
public class Area {
	//成员变量都用引用类型，如果基本类型会为空赋默认值
	private Integer areaId;// ID
	private String areaName;// 名称
	private Integer priority;// 权重
	private Date creatTime;// 创建时间
	private Date lastEdiTime;// 更新时间
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getLastEdiTime() {
		return lastEdiTime;
	}
	public void setLastEdiTime(Date lastEdiTime) {
		this.lastEdiTime = lastEdiTime;
	}

}
