package com.imooc.o2o.service1.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryOperationException;
import com.imooc.o2o.service1.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired 
	private JedisUtil.Strings jedisStrings;
	
	private static Logger logger=LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key=SHOPCATEGORYKEY;
		List<ShopCategory> shopCategoryList=null;
		ObjectMapper mapper=new ObjectMapper();
		
		if (shopCategoryCondition==null) {//列出所有一级类别
			key=key+"_allfirstleval";
		}else if(shopCategoryCondition!=null&&shopCategoryCondition.getParent()!=null
				&&shopCategoryCondition.getParent().getShopCategoryId()!=null) {//列出某个一级类别下指定的二级类别
			key=key+"_parent"+shopCategoryCondition.getParent().getShopCategoryId();
		}
		else if (shopCategoryCondition!=null) {//列出所有二级类别
			key=key+"_allsecondleval";
			
		}
		
		if (!jedisKeys.exits(key)) {
			shopCategoryList=shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String jsonString=null;
			try {
				jsonString=mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
				
			}
			jedisStrings.set(key, jsonString);
		}else {
			String jsonString=jedisStrings.get(key);
			JavaType javaType =mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList=mapper.readValue(jsonString, javaType);
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			}
		}
		
		return shopCategoryList;
	}

}
