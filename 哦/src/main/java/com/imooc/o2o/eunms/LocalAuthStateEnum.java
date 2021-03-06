package com.imooc.o2o.eunms;

public enum LocalAuthStateEnum {
LOGINFAIL(-1,"账号输入错误"),SUCCESS(0,"操作成功"),NULL_AUTH_INFO(-1006,"注册信息为空"),
ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地账号");
	
	private int state;
	private String stateInfo;
	private LocalAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public int getState(){
		return state;
	}
	public String getStateInfo(){
		return stateInfo;
	}
	public static LocalAuthStateEnum stateOf(int index){
		for (LocalAuthStateEnum state : values()) {
			if (state.getState()==index) {
				return state;
			}
		}
		return null;
	}
}
