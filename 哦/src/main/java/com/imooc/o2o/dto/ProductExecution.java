package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.eunms.ProductStateEnum;

public class ProductExecution {

	private int state;
	//状态标识
	private String stateInfo;
	//商品数量
	private int count;
	//操作的product（增删改使用）
	private Product product;
	//获取的product列表（查询商品列表使用）
	private List<Product> productList;
	
	public ProductExecution(){
		
	}
	//失败的构造器
	public ProductExecution(ProductStateEnum stateEunm){
		this.state=stateEunm.getState();
		this.stateInfo=stateEunm.getStateInfo();
	}
	//操作成功的构造器
	public ProductExecution(Product product,ProductStateEnum stateEunm){
		this.state=stateEunm.getState();
		this.stateInfo=stateEunm.getStateInfo();
		this.product=product;
	}
	//操作成功的构造器
	public ProductExecution(List<Product> productList,ProductStateEnum stateEunm){
		this.state=stateEunm.getState();
		this.stateInfo=stateEunm.getStateInfo();
		this.productList=productList;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
}
