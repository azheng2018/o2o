package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.eunms.LocalAuthStateEnum;

public class LocalAuthExcution {
//结果状态
	private int state;
//状态标识
	private String stateInfo;
//总数
	private int count;
	private LocalAuth localAuth;
	private List<LocalAuth> localAuthList;
	
	//失败的构造器
	public LocalAuthExcution(LocalAuthStateEnum stateEnum){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//成功的构造器
	public LocalAuthExcution(LocalAuthStateEnum stateEnum,LocalAuth localAuth){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.localAuth=localAuth;
	}
	//成功的构造器
	public LocalAuthExcution(LocalAuthStateEnum stateEnum,List<LocalAuth> localAuthList){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.localAuthList=localAuthList;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public LocalAuth getLocalAuth() {
		return localAuth;
	}
	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}
	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}
	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}
	
}
