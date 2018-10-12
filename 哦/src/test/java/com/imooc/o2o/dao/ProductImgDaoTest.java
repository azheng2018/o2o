//package com.imooc.o2o.dao;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.imooc.o2o.entity.ProductImg;
//
//
//public class productImgDaoTest {
//@Autowired 
//private ProductImgDao productImgDao;
//
//	@Test
//	public void testBatchInsertProductImg() throws Exception{
//		ProductImg p1=new ProductImg();
//		p1.setCreateTime(new Date());
//		p1.setImgAddr("test1");
//		p1.setImgDesc("图片1");
//		p1.setPriority(1);
//		p1.setProductId(1L);
//		ProductImg p2=new ProductImg();
//		p2.setCreateTime(new Date());
//		p2.setImgAddr("test2");
//		p2.setImgDesc("图片2");
//		p2.setPriority(2);
//		p2.setProductId(1L);
//		List<ProductImg> productImgList=new ArrayList<ProductImg>();
//		productImgList.add(p1);
//		productImgList.add(p2);	
//		int effectdNum=productImgDao.batchInsertProductImg(productImgList);
//		System.out.println(".d..");
//		assertEquals(2,effectdNum);
//		System.out.println("...");
//	}
//}
package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testABatchInsertProductImg() throws Exception {
		// productId为1的商品里添加两个详情图片记录
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}
	@Test
	public void testBQueryProductImgList() {
		// 检查productId为1的商品是否有且仅有两张商品详情图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(6L);
		assertEquals(2, productImgList.size());
	}
	
	@Test
	public void testCDeleteProductImgByProductId(){
	long productId=1;
	int effectNum=productImgDao.deleteProductImgByProductId(productId);
	assertEquals(2, effectNum);
	}

}