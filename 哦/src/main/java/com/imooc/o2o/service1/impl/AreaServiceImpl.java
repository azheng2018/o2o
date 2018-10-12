package com.imooc.o2o.service1.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service1.AreaService;
@Service//告诉springIOC，ServiceImpl需要托管，别的类调用AreaService，springIOC会将AreaServiceImpl动态的注入到areaDao这个接口里面
public class AreaServiceImpl implements AreaService{
@Autowired
private AreaDao areaDao;
@Autowired
private JedisUtil.Keys jedisKeys;
@Autowired
private JedisUtil.Strings jedisString;

private static String AREALISTKEY="arealist";
private static Logger logger=LoggerFactory.getLogger(AreaServiceImpl.class);

	@Override
	@Transactional
	public List<Area> getAreaList() {
		String key=AREALISTKEY;
		List<Area> areaList=null;
		ObjectMapper mapper=new ObjectMapper();
		if (!jedisKeys.exits(key)) {
			//如果有没有key值，说明是要缓存
			areaList=areaDao.queryArea();
			String jsonString = null;
			try {
				jsonString=mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisString.set(key, jsonString);
		}else{
			//如果有key，则从缓存中取value
			String jsonString=jedisString.get(key);
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList=mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	
		
		
	}

}
