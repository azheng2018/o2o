package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 删除商铺的商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
	
	/**
	 * 批量添加商铺的商品类别，返回值为int类型指的是影响的行数
	 * 
	 * @param productCategoryList
	 * @return
	 */
	
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * 通过shopId查询商铺的商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
}
