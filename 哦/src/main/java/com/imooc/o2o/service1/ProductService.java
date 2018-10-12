package com.imooc.o2o.service1;


import java.util.List;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImg
	 * @return
	 */
	ProductExecution modifyProduct(Product product,ImageHolder thumbnail,
			List<ImageHolder> productImg) throws ProductOperationException;

	/**
	 * 添加商品信息以及图片处理
	 * 需要处理商品信息，商品缩略图，商品详情图
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,ImageHolder thumbnail,
List<ImageHolder> productImgList) throws ProductOperationException;
	
	/**
	 * 通过商品id查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 查询商品列表并分页，可输入条件有：商品名称（模糊）,商品类别，商品状态，商铺id
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution queryProductList(Product productCondition,int pageIndex,int pageSize);
}
