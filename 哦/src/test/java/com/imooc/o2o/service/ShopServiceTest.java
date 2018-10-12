package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExcution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.eunms.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service1.ShopService;

public class ShopServiceTest extends BaseTest {
@Autowired
private ShopService shopService;
@Test
@Ignore
public void testAddShop() throws FileNotFoundException{
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
	shop.setShopName("测试店铺3");
	shop.setShopDesc("test3");
	shop.setShopAddr("test3");
	shop.setPhone("18814374735");
	shop.setCreateTime(new Date());
	shop.setEnableStatus(ShopStateEnum.CHECK.getState());
	shop.setAdvice("审核中");
	File shopImg=new File("F:/phonto/image/huangzi.jpg");
	InputStream inputStream=new FileInputStream(shopImg);
	ImageHolder imageHolder=new ImageHolder(inputStream,shopImg.getName());
	ShopExcution se=shopService.addShop(shop, imageHolder);
	assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
}
@Test

public void testModifyShop()throws FileNotFoundException,ShopOperationException{
	Shop shop=new Shop();
	shop.setShopId(104L);
	shop.setShopName("测试修改店铺名称111");
	File shopImg=new File("F:/phonto/xiazi.jpg");
	InputStream inputStream=new FileInputStream(shopImg);
	ImageHolder imageHolder=new ImageHolder(inputStream, shopImg.getName());
	ShopExcution shopExcution=shopService.modifyShop(shop, imageHolder);
	System.out.println(shopExcution.getShop().getShopName());
//	System.out.println(shopExcution.getShop().getShopImg());
}
@Test
@Ignore
public void testGetShopList(){
	Shop shop=new Shop();
	ShopCategory shopCategory=new ShopCategory();
	shopCategory.setShopCategoryId(5L);
	shop.setShopCategory(shopCategory);
	ShopExcution se=shopService.getShopList(shop, 2, 5);
	System.out.println("店铺列表数为"+se.getShopList().size());
	System.out.println("店铺总数"+se.getCount());
}
}
