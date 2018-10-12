package com.imooc.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {
/**
 * Redis连接池对象
 */
	private JedisPool jedisPool;
	
	public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,final int port){
		try {
			jedisPool=new JedisPool(poolConfig,host,port);
		} catch (Exception e) {
			
		}
	}
/**
 * 获取Jedis连接池对象
 * @return
 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}
/**
 * 注入Jedis连接池对象
 * @param jedisPool
 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
