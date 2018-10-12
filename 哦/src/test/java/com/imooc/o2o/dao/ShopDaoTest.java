package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
@Autowired
private ShopDao shopDao;
@Test
@Ignore
public void testInsertShop(){
	Shop shop=new Shop();
	PersonInfo owner=new PersonInfo();
	Area area=new Area();
	ShopCategory shopCategory=new ShopCategory();
	owner.setUserId(1L);
	area.setAreaId(2);
	area.setAreaName("ssss");
	area.setCreatTime(new Date());
	area.setPriority(1);
	shopCategory.setShopCategoryId(1L);
	shop.setArea(area);
	shop.setOwner(owner);
	shop.setShopCategory(shopCategory);
	shop.setShopName("测试店铺");
	shop.setShopDesc("test");
	shop.setShopAddr("test");
	shop.setPhone("18814374735");
	shop.setShopImg("test");
	shop.setCreateTime(new Date());
	shop.setEnableStatus(1);
	shop.setAdvice("审核中");
	int effectedNum=shopDao.insertShop(shop);
	assertEquals(1,effectedNum);
}

@Test
@Ignore
public void updateShop(){
	Shop shop=new Shop();
	shop.setShopId(37L);
	shop.setShopDesc("好吃的味道");
	shop.setShopAddr("广州");
	shop.setLastEditTime(new Date());
	int efftectdNum=shopDao.updateShop(shop);
	assertEquals(1, efftectdNum);
}
@Test
@Ignore
public void testQueryByIdShop(){
	long shopId=100;
	Shop shop=shopDao.queryByShopId(shopId);
	System.out.println(shop.getArea().getAreaName());
	System.out.println(shop.getArea().getAreaId());
	System.out.println(shop.getShopName());
}
@Test
@Ignore
public void testQueryShopList(){
	
	Shop shopCondition=new Shop();
	PersonInfo owner=new PersonInfo();
	owner.setUserId(1L);
	shopCondition.setOwner(owner);
List<Shop> shopList=shopDao.queryShopList(shopCondition, 1, 5);
	System.out.println("查询的店铺列表大小"+shopList.size());
	int count=shopDao.queryShopCount(shopCondition);
	System.out.println("店铺总数"+count);
	ShopCategory shopCategory=new ShopCategory();
	shopCategory.setShopCategoryId(5L);
	shopCondition.setShopCategory(shopCategory);
	List<Shop> shopList1=shopDao.queryShopList(shopCondition, 0, 5);
	System.out.println("查询的店铺列表大小"+shopList1.size());
	int count1=shopDao.queryShopCount(shopCondition);
	System.out.println("店铺总数"+count1);
}

@Test
public void testQueryShopListAndCount(){
	Shop shopCondition=new Shop();
	ShopCategory childShopCategory=new ShopCategory();
	ShopCategory parentShopCategory=new ShopCategory();
	parentShopCategory.setShopCategoryId(4L);
	childShopCategory.setParent(parentShopCategory);
	shopCondition.setShopCategory(childShopCategory);
	List<Shop> shopList=shopDao.queryShopList(shopCondition, 0, 10);
	int count=shopDao.queryShopCount(shopCondition);
	System.out.println("1  "+shopList.size());
	System.out.println("2  "+count);
}
}
