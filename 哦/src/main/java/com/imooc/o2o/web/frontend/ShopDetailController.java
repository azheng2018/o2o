package com.imooc.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service1.ProductCategoryService;
import com.imooc.o2o.service1.ProductService;
import com.imooc.o2o.service1.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/listshopdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String,Object>();
	Shop shop=null;
	List<ProductCategory> productCategoryList=null;
	long shopId=HttpServletRequestUtil.getLong(request, "shopId");
	if (shopId!=-1) {
		//获取店铺信息
		shop=shopService.getShopById(shopId);
		//获取店铺商品列表信息
		productCategoryList=productCategoryService.getProductCategoryList(shopId);
		modelMap.put("shop", shop);
		modelMap.put("productCategoryList", productCategoryList);
		modelMap.put("success", true);
	}else {
		modelMap.put("success", false);
		modelMap.put("errMsg", "empty shopId");
	}
	return modelMap;
}
	//根据查询条件获取店铺下的商品列表
	@RequestMapping(value="/listproductsbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request){
		Map<String, Object>  modelMap=new HashMap<String,Object>();
		//获取页码
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每页显示条数
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		//获取店铺id
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		//空值判断
		if (pageIndex>-1&&pageSize>-1&&shopId>-1) {
			//尝试获取商品类别id
			long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
			//尝试获取商品名称
			String productName=HttpServletRequestUtil.getString(request, "productName");
			//组合成查询条件
			Product productCondition=compactProductCondition4Search(shopId,productCategoryId,productName);
			//按照传入的查询条件以及分页信息返回商品列表和商品总数
			ProductExecution pe=productService.queryProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	//组合查询条件，并将条件封装在productCondition对象中
	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition=new Product();
		Shop shop=new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId!=-1L) {
			//查询某个商品类别下的商品列表
			ProductCategory productCategory=new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName!=null) {
			//查询名字包含productName的商品
			productCondition.setProductName(productName);
		}
		productCondition.setEnableStatus(1);
		return productCondition;
	}
	
}
