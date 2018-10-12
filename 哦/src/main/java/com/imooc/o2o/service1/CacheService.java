package com.imooc.o2o.service1;

public interface CacheService {
/**
 * 从缓存删除key
 */
	void removeFromCache(String keyPrefix);
}
