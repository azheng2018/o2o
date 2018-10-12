package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Test
	public void testBQueryByShopId()throws Exception{
		long shopId=100;
		List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(shopId);
		System.out.println( "店铺所含有的商品类别数目"+ productCategoryList.size());
	}
	@Test
	public void testABatchInsertProductCategory(){
		ProductCategory p1=new ProductCategory();
		ProductCategory p2=new ProductCategory();
		p1.setProductCategoryName("商品类别1");
		p1.setPriority(3);
		p1.setCreatTime(new Date());
		p1.setShopId(100L);
		p2.setProductCategoryName("商品类别2");
		p2.setPriority(4);
		p2.setCreatTime(new Date());
		p2.setShopId(100L);
		List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
		productCategoryList.add(p1);
		productCategoryList.add(p2);
		int effectNum=productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2,effectNum);
	}
	
	@Test
	public void testCDeleteProductCategory() throws Exception{
//		long shopId=100L;
//		Shop shop=new Shop();
//		shop.setShopId(shopId);
//		int effectNum=productCategoryDao.deleteProductCategory(10, shopId);
//		assertEquals(1, effectNum);
		long shopId=100;
		List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory pc : productCategoryList) {
			if (pc.getProductCategoryName().equals("商品类别1")||pc.getProductCategoryName().equals("商品类别2")) {
				int effectNum=productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1, effectNum);
			}
		}
		
	}

}
