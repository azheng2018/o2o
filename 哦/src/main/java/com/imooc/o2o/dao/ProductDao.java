package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	/**
	 * 通过productId查询商品信息
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);
	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	
	/**
	 * 查询商品列表及分页，可输入的条件有：商品名（模糊），商品状态，类别，商铺id
	 * @param producCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition")Product productCondition,@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	/**
	 * 查询所有的商品总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount (@Param("productCondition")Product productCondition);
	
	/**
	 * 删除商品类别前，把商品类别ID置为空
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
