package com.imooc.o2o.service1;

import java.util.List;

import com.imooc.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 获取商铺类别列表
	 * @param shopCategoryCondition
	 * @return
	 */
	public  static final String SHOPCATEGORYKEY="shopcategorylist";
List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
