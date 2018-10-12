package com.imooc.o2o.web.shopadmin;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;import com.imooc.o2o.dao.ShopCategoryDaoTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExcution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.eunms.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service1.AreaService;
import com.imooc.o2o.service1.ShopCategoryService;
import com.imooc.o2o.service1.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	//店铺管理页
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String,Object>();
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId<=0) {
			Object currentShopObj=request.getSession().getAttribute("currentShop");
			if (currentShopObj==null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop=(Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else {//如果前端传来的shopId大于0，则建立一个Shop对象，并将shopId赋给该对象
			Shop currentShop=new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object>getShopList(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String,Object>();
//	PersonInfo user=new PersonInfo();
//	user.setUserId(8L);
//	user.setName("test");
//	request.getSession().setAttribute("user", user);
PersonInfo	user=(PersonInfo) request.getSession().getAttribute("user");
	try {
		Shop shopCondition=new Shop();
		shopCondition.setOwner(user);
		ShopExcution se=shopService.getShopList(shopCondition, 0, 100);
		modelMap.put("shopList", se.getShopList());
		//列出店铺成功后，将店铺放入session中作为权限验证依据，即该账号只能操作他的店铺
		request.getSession().setAttribute("shopList",se.getShopList());
		modelMap.put("user", user);
		modelMap.put("success", true);
	} catch (Exception e) {
		modelMap.put("success", false);
		modelMap.put("errMsg", e.getMessage());
	}
	return modelMap;
	}
		
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		// 1.接受并转换相应的参数，包括店铺信息和图片信息
		ObjectMapper mapper = new ObjectMapper();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;// 接收图片，spring自带的类
		// 文件上传解析器，解析request的文件信息
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());// 获取request的本次会话的相关内容
		// commonsMultipartResolve判断request是否有上传的文件流,如果有则将request作转换为multipartHttpServletRequest
		// 从而提取出相应的文件流
		if (commonsMultipartResolver.isMultipart(request)) {
			// 强制转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} 
		else {// 如果上传图片不是必填项，则去掉该段代码
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.注册店铺
		// 3.返回结果
		if (shop != null && shopImg != null) {
			
//			
		PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");//注册店铺需要用户
			//先登录，用户信息保存在session中，tomcat的session一般保留30min
		shop.setOwner(owner);
			ShopExcution se;
			
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
				se = shopService.addShop(shop,imageHolder);
				if (se.getState()==ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//用户可有多个店铺，在session保存一个店铺列表来显示该用户可操作哪个店铺
					//该用户可操作的店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList==null||shopList.size()==0) {
						shopList=new ArrayList<Shop>();
					}
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList",shopList);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());//se.getStateInfo返回状态的注释
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	
	}
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map< String, Object> modelMap=new HashMap<String,Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
			if (shopId>-1) {
				try {
				Shop shop=shopService.getShopById(shopId);
				List<Area> areaList=areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
				
	return modelMap;
	}
	@RequestMapping(value = "/modifyshop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		// 1.接受并转换相应的参数，包括店铺信息和图片信息
		ObjectMapper mapper = new ObjectMapper();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;// 接收图片，spring自带的类
		// 文件上传解析器，解析request的文件信息
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());// 获取request的本次会话的相关内容
		// commonsMultipartResolve判断request是否有上传的文件流,如果有则将request作转换为multipartHttpServletRequest
		// 从而提取出相应的文件流
		if (commonsMultipartResolver.isMultipart(request)) {
			// 强制转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} 
//		else {// 如果上传图片不是必填项，则去掉该段代码（由于图片上传是非必填，所以去掉）
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "上传图片不能为空");
//			return modelMap;
//		}
		// 2.编辑店铺
		if (shop != null && shop.getShopId() != null) {
			ShopExcution se;
			try {
				if (shopImg==null) {//如果有图片上传则处理
					se = shopService.modifyShop(shop,null);
				}else {
					ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
					se = shopService.modifyShop(shop, imageHolder);
				}
				if (se.getState()==ShopStateEnum.SUCCESS.getState()) {//由于店铺是已存在的
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());//se.getStateInfo返回状态的注释
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
			return modelMap;
		}
	
	}
	
}