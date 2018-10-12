package com.imooc.o2o.service1.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExcution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.eunms.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service1.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExcution addShop(Shop shop, ImageHolder thumbnail) {
		// 空值判断
		if (shop == null) {
			return new ShopExcution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);

			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");// 使用ShopOperationException是如果有异常会事务回滚
			} else {
				if (thumbnail.getImage() != null) {
					// 存储图片
					try {
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}

				}
			}

		} catch (Exception e) {

			throw new ShopOperationException("addShop error" + e.getMessage());

		}

		return new ShopExcution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImgPath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);

	}

	@Override
	public Shop getShopById(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExcution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExcution(ShopStateEnum.NULL_SHOP);// 如果商店为空不能修改，如果商店id为空也不能修改
		}
		try {
			// 1.判断是否需要处理图片
			if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if (tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail);
			}
			// 2.更新店铺信息
			shop.setLastEditTime(new Date());
			int effectNum=shopDao.updateShop(shop);	
			if (effectNum<=0) {
				return new ShopExcution(ShopStateEnum.INNER_ERROR);
			}else {
				System.out.println("???");
				shop=shopDao.queryByShopId(shop.getShopId());
				return new ShopExcution(ShopStateEnum.SUCCESS,shop);
			}
		} catch (Exception e) {
			throw new ShopOperationException("modifyShop error:"+e.getMessage());
		}
		
	}

	@Override
	public ShopExcution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
	int rowIndex=PageCalculator.caculatorRowIndex(pageIndex, pageSize);
	List<Shop> shopList=shopDao.queryShopList(shopCondition, rowIndex, pageSize);
	int count=shopDao.queryShopCount(shopCondition);
	ShopExcution sc=new ShopExcution();
	if (shopList!=null) {
		sc.setShopList(shopList);
		sc.setCount(count);
	}else {
		sc.setState(ShopStateEnum.INNER_ERROR.getState());
	}
		return sc;
	}
	

}
