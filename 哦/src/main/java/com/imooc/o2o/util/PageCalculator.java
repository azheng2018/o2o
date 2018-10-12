package com.imooc.o2o.util;

public class PageCalculator {
public static int caculatorRowIndex(int pageIndex,int pageSize){
	
	return (pageIndex>0)?(pageIndex-1)*pageSize:0;//(pageIndex-1)是页码，如果pageIndex=1，
	//pageSize=5，说明是第一页，选取前5跳数据，如果pageIdex=2，说明是第二页，选择第6-10条数据
}
}
