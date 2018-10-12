package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{
@Autowired
private AreaDao areaDao;
@Test
public void testQueryArea(){
	//queryArea返回的是集合对象Area，所以用list接收
	List<Area> areaList=areaDao.queryArea();
	assertEquals(2,areaList.size());
	
}

}
