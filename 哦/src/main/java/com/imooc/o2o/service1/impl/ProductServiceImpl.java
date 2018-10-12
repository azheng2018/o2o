package com.imooc.o2o.service1.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.eunms.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service1.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	// 1.处理缩略图，获取缩略图相对路径并赋值给product
	// 2.往tb_product写入商品信息，获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img中
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 先判断product是否为空或者所属的店铺是否为空
		if (product != null && product.getShop() != null && product.getShop().getShopId() > 0) {
			product.setCreateTime(new Date());
			product.setEnableStatus(1);
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectNum = productDao.insertProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败");
			}
			// 如果商品详情图不为空则添加
			if (productImgList != null && productImgList.size() > 0) {
				addProductImgList(product, productImgList);
			}
			return new ProductExecution(product, ProductStateEnum.SUCCESS);

		} else {
			// 传参为空值则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
//添加商品信息有缩略图
	private void addThumbnail(Product product, ImageHolder imageHolder) {
		String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(imageHolder, dest);
		product.setImgAddr(thumbnailAddr);
	}
//添加商品信息有商品详情图
	private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
		String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		for (ImageHolder imageHolder : imageHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);// 取出每张图片的相对路径
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setCreateTime(new Date());
			productImg.setProductId(product.getProductId());
			productImgList.add(productImg);
		}
		if (productImgList.size() >= 0) {
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品详情图失败");
				}

			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图失败" + e.toString());
			}

		}
	}
//	private void deleteProductImageList(long productId){
//		//根据原来的product获取productImgList
//		List<ProductImg> productImgList=productImgDao.queryProductImgList(productId);
//		for (ProductImg productImg : productImgList) {
//			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
//	}
//	productImgDao.deleteProductImgByProductId(productId);
//	}
	/**
	 * 删除某个商品下的所有详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImageList(long productId) {
		// 根据productId获取原来的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		// 干掉原来的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// 删除数据库里原有图片的信息
		productImgDao.deleteProductImgByProductId(productId);
	}
	
	//1、若缩略图参数有值，则处理缩略图，若原先存在缩略图，则先删除原来的缩略图再添加新图
	//之后获取缩略图相对路径给product
	//2、若商品详情图列表参数有值，则进行同样的操作
	//3、将tb_product_img下面的该商品原先的商品详情图全部清除
	//4、更新tb_product和tb_product_img的信息
	@Override
	@Transactional
//	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
//			throws ProductOperationException {
//		if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
//			product.setLastEditTime(new Date());
//			//如果商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
//			if (thumbnail!=null) {
//				//先获取原有信息，因为原有信息有缩略图信息
//				Product tempProduct=productDao.queryProductById(product.getProductId());	
//				if (tempProduct.getImgAddr()!=null) {
//					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
//				}
//				addThumbnail(product, thumbnail);	
//			}
//			//如果有新存入的商品详情图，将原来的删除并添加新的商品详情图
//			if (productImgHolderList!=null&&productImgHolderList.size()>0) {
//				deleteProductImageList(product.getProductId());
//				addProductImgList(product, productImgHolderList);
//			}
//			try {
//				//更新商品信息
//				int effectNum=productDao.updateProduct(product);
//				if (effectNum<=0) {
//					throw new ProductOperationException("更新商品信息失败");
//				}
//					return new ProductExecution(product,ProductStateEnum.SUCCESS);
//				
//			} catch (Exception e) {
//				throw new ProductOperationException("更新商品信息失败");
//			}
//			
//		}else {
//			return new ProductExecution(ProductStateEnum.EMPTY);
//		}
//	}
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置上默认属性
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if (thumbnail != null) {
				// 先获取一遍原有信息，因为原来的信息里有原图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// 如果有新存入的商品详情图，则将原先的删除，并添加新的图片
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				deleteProductImageList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}
			try {
				// 更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(product, ProductStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}
	@Override
	public ProductExecution queryProductList(Product productCondition, int pageIndex, int pageSize) {
		//页码转换为数据库的行码
		int rowIndex=PageCalculator.caculatorRowIndex(pageIndex, pageSize);
		List<Product> productList=productDao.queryProductList(productCondition, rowIndex, pageSize);
		//基于同样的查询条件返回该条件下商品的总数
		int count=productDao.queryProductCount(productCondition);
		ProductExecution pe=new ProductExecution();
		pe.setCount(count);
		pe.setProductList(productList);
		return pe;
	}
}
