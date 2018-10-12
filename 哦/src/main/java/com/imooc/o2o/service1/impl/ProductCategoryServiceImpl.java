package com.imooc.o2o.service1.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.eunms.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service1.ProductCategoryService;

//Service的意思是需要spring来托管这个类
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;


	@Override
	public List<ProductCategory>  getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {

		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNum <= 0) {
					throw new ProductCategoryOperationException("商品类别创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error" + e.getMessage());
			}

		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	//该方法分两步执行，需要事务管理1、将商品类别下的商品ID置为空 2、删除该商品类别
	//使用事务好处：如果第一步操作成功，并不急着提交，等待第二步也操作成功再一起提交，达到回滚的目的
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//  解除tb_product里的商品与该productCategoryId的关联

		try {
			int effectNum=productDao.updateProductCategoryToNull(productCategoryId);
			if (effectNum<0) {
				throw new ProductCategoryOperationException("商品类别更新失败");
				
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error"+e.getMessage());
		}
		//删除该productCategory
		try {
			int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectNum <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}

		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error" + e.toString());
		}
	}

}
