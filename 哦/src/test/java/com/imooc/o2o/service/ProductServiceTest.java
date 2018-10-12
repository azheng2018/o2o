package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.eunms.ProductStateEnum;
import com.imooc.o2o.service1.ProductService;

public class ProductServiceTest extends BaseTest{
@Autowired
private ProductService productService;
	@Test
	public void testModifyProductService() throws FileNotFoundException{
		Shop shop=new Shop();
		shop.setShopId(105L);
	ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(7L);
		Product product=new Product();
		product.setProductId(6L);
		product.setNormalPrice("200");
		product.setProductName("烧鸡");
		product.setProductDesc("慢餐");
		product.setShop(shop);
		product.setProductCategory(productCategory);
		//缩略图文件流
		File thumbnailFile=new File("F:/phonto/qinnv.jpg");
		InputStream is=new FileInputStream(thumbnailFile);
		ImageHolder thumbnail=new ImageHolder(is, thumbnailFile.getName());
		//商品详情图文件流
		File productImg1=new File("F:/phonto/qinnv.jpg");
		InputStream is1=new FileInputStream(productImg1);
		File productImg2=new File("F:/phonto/qinnv.jpg");
		InputStream is2=new FileInputStream(productImg2);
		List<ImageHolder> productImageList=new ArrayList<ImageHolder>();
		productImageList.add(new ImageHolder(is1, productImg1.getName()));
		productImageList.add(new ImageHolder(is2, productImg2.getName()));
		//添加商品并验证
		ProductExecution pe=productService.modifyProduct(product, thumbnail, productImageList);
		System.out.println(pe.getStateInfo());
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
		
		
	}
	@Test
	@Ignore
	public void testAddProductService() throws FileNotFoundException{
		Shop shop=new Shop();
		shop.setShopId(100L);
	ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(6L);
		Product product=new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setLastEditTime(new Date());
		product.setNormalPrice("2");
		product.setPriority(2);
		product.setProductName("烧鸭");
		product.setProductDesc("快餐");
		product.setShop(shop);
		product.setProductCategory(productCategory);
		//缩略图文件流
		File thumbnailFile=new File("F:/phonto/xiazi.jpg");
		InputStream is=new FileInputStream(thumbnailFile);
		ImageHolder thumbnail=new ImageHolder(is, thumbnailFile.getName());
		//商品详情图文件流
		File productImg1=new File("F:/phonto/qinnv.jpg");
		InputStream is1=new FileInputStream(productImg1);
		File productImg2=new File("F:/phonto/guanghui.jpg");
		InputStream is2=new FileInputStream(productImg2);
		List<ImageHolder> productImageList=new ArrayList<ImageHolder>();
		productImageList.add(new ImageHolder(is1, productImg1.getName()));
		productImageList.add(new ImageHolder(is2, productImg2.getName()));
		//添加商品并验证
		ProductExecution pe=productService.addProduct(product, thumbnail, productImageList);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
		
		
	}
}
