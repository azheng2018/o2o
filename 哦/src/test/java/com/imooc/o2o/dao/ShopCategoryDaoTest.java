package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service1.AreaService;
import com.imooc.o2o.service1.ShopCategoryService;
import com.imooc.o2o.service1.ShopService;

public class ShopCategoryDaoTest extends BaseTest{
@Autowired
private ShopCategoryDao shopCategoryDao;

@Autowired
private ShopService shopService;
@Autowired
private ShopCategoryService shopCategoryService;
@Autowired
private AreaService areaService;
@Test
public void testQueryShopCategory(){

	ShopCategory shopCategory1=new ShopCategory();
	ShopCategory shopCategory2=new ShopCategory();
	shopCategory1.setShopCategoryId(4L);
	shopCategory2.setParent(shopCategory1);
	List<ShopCategory> sList2=shopCategoryDao.queryShopCategory(shopCategory2);
	assertEquals(1,sList2.size());
	System.out.println(sList2.get(0).getShopCategoryName());
	List<ShopCategory> s3=shopCategoryDao.queryShopCategory(null);
	System.out.println("...");
	System.out.println(s3.size());
	
}

@Test
public void tesShopCategory(){

	Map<String, Object> modelMap = new HashMap<String, Object>();
	List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
	List<Area> areaList = new ArrayList<Area>();

		shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
		areaList = areaService.getAreaList();
/*		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("areaList", areaList);
		modelMap.put("success", true);*/
		System.out.println(shopCategoryList);
		System.out.println(areaList);
}


}
