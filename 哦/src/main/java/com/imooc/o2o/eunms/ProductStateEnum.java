package com.imooc.o2o.eunms;

public enum ProductStateEnum {
OFFLINE(-1,"非法商品"),DOWM(0,"下架"),SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),
EMPTY(-1002,"商品为空");
	private int state;
	private String stateInfo;
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
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
	public static ProductStateEnum stateOf(int index){
		for (ProductStateEnum state : values()) {
			if (index==state.getState()) {
				return state;
			}
		}
		return null;
	}
}
