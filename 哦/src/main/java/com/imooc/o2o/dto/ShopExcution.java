package com.imooc.o2o.dto;

import java.io.File;
import java.util.List;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.eunms.ShopStateEnum;

public class ShopExcution {
	// 结果状态
	private int state;
	// 状态表示
	private String stateInfo;
	// 店铺数量
	private int count;
	// 操作的shop（增删改时用）
	private Shop shop;
	// shop的列表（查询店铺时用）
	private List<Shop> shopList;

	public ShopExcution() {

	}

	// 店铺操作失败时所用的构造器
	public ShopExcution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功所用的构造器
	public ShopExcution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}

	// 店铺操作成功所用的构造器
	public ShopExcution(ShopStateEnum stateEnum,  List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
		
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
