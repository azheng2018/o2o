package com.imooc.o2o.service1.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service1.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{
@Autowired
private HeadLineDao headLineDao;
@Autowired
private JedisUtil.Keys jedisKeys;
@Autowired
private JedisUtil.Strings jedisStrings;


private static Logger logger=LoggerFactory.getLogger(HeadLineServiceImpl.class);

	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
		String key=HEADLINEKEY;
		List<HeadLine> headLineList=null;
		ObjectMapper mapper=new ObjectMapper();
		
		if (headLineCondition!=null&&headLineCondition.getEnableStatus()!=null) {
			key=key+"_"+headLineCondition.getEnableStatus();
		}
		if (!jedisKeys.exits(key)) {
			headLineList=headLineDao.queryHeadLine(headLineCondition);
	 		String jsonString=null;
	 		try {
				jsonString=mapper.writeValueAsString(headLineList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
	 		jedisStrings.set(key, jsonString);
		}else {
			String jsonString=jedisStrings.get(key);
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				headLineList=mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		}
		return headLineList;
 		
	}

}
