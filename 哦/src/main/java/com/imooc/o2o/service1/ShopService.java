package com.imooc.o2o.service1;



import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExcution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分特返回店铺类表
	 * @param shopCondition
	 * @param index
	 * @param pageSize
	 * @return
	 */
	ShopExcution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	/**
	 * 根据店铺id获取店铺信息
	 * 
	 * @param shop
	 * @return
	 */
	Shop getShopById(long shipId);

	/**
	 * 更新店铺信息，包括对图片的处理
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExcution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	/**
	 * 添加店铺，包括对图片的处理
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExcution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
