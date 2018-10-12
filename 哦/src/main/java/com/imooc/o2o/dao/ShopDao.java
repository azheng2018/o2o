package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * 分页查询店铺，可输入的条件有：店铺名称（模糊查询），店铺列表，店铺ID，店铺状态，owner
	 * @param shopCondition 查询的条件
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize 返回的条数
	 * @return
	 */
	//因为参数有多个，所以需要加Param的唯一标识去取参数
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */ 
 int  insertShop(Shop shop);
/**
 * 更新店铺信息
 * @param shop
 * @return
 */
int updateShop(Shop shop);
/**
 * 查询店铺信息
 * @param
 * @return
 */
Shop queryByShopId(long longId);
}
