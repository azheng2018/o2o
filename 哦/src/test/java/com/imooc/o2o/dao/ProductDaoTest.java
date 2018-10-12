package com.imooc.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;
import com.mysql.fabric.xmlrpc.base.Data;

import static org.junit.Assert.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	@Test
	@Ignore
	public void testAInsertProduct(){
		Shop shop=new Shop();
		shop.setShopId(100L);
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(6L);
		ProductImg productImg1=new ProductImg();
		productImg1.setProductId(1L);
		productImg1.setCreateTime(new Date());
		productImg1.setImgAddr("test1");
		productImg1.setImgDesc("test1");
		productImg1.setPriority(1);
		productCategory.setProductCategoryId(6L);
		
		Product p1=new Product();
		p1.setProductName("商品1");
		p1.setCreateTime(new Date());
		p1.setEnableStatus(1);
		p1.setImgAddr("test");
		p1.setLastEditTime(new Date());
		p1.setNormalPrice("2");
		p1.setPriority(1);
		p1.setShop(shop);
		p1.setProductCategory(productCategory);
		int effectNum=productDao.insertProduct(p1);
		assertEquals(1,effectNum);
	
	}
	@Test
	public void testBQueryProductById(){
		long productId=1;
		ProductImg productImg1=new ProductImg();
		productImg1.setProductId(productId);
		productImg1.setCreateTime(new Date());
		productImg1.setImgAddr("test1");
		productImg1.setImgDesc("test1");
		productImg1.setPriority(1);
		ProductImg productImg2=new ProductImg();
		productImg2.setProductId(productId);
		productImg2.setCreateTime(new Date());
		productImg2.setImgAddr("test2");
		productImg2.setImgDesc("test2");
		productImg2.setPriority(1);
		List<ProductImg> productImgList=new ArrayList<ProductImg>();
		productImgList.add(productImg2);
		productImgList.add(productImg1);
		int effectNum=productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectNum);
		//查询productId为1的商品信息并校验返回的详情图list的大小
		Product product=productDao.queryProductById(productId);
		assertEquals(2, product.getProductImgList().size());
		int effectNum2=productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectNum2);
	}
	@Test
	public void testCUpdateProduct(){
		Product product=new Product();
		ProductCategory productCategory=new ProductCategory();
		Shop shop=new Shop();
		shop.setShopId(100L);
		productCategory.setShopId(shop.getShopId());
		productCategory.setProductCategoryId(6L);
		product.setProductCategory(productCategory);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductName("改名后的大西瓜");
		int effectNum=productDao.updateProduct(product);
		assertEquals(1, effectNum);
		
	}
	
	@Test
	public void testDQueryProductList(){
		Product productCondition=new Product();
		List<Product> productList=productDao.queryProductList(productCondition, 0, 10);
		assertEquals(10, productList.size());
		int count=productDao.queryProductCount(productCondition);
		assertEquals(35, count);
		productCondition.setProductName("烧");
		List<Product> productList2=productDao.queryProductList(productCondition, 0, 10);
		assertEquals(3, productList2.size());
		int count2=productDao.queryProductCount(productCondition);
		assertEquals(3, count2);
		
	}
	
	@Test
	public void testEUpdateProductCategoryToNull(){
		int effectNum=productDao.updateProductCategoryToNull(1L);
		assertEquals(1, effectNum);
	}
}
