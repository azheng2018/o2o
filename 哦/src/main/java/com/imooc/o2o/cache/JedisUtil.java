package com.imooc.o2o.cache;


import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;


public class JedisUtil {
/**
 * 操作key的方法
 */
	public Keys KEYS;
	//对存储结构为string类型的操作
	public Strings STRINGS;
	private JedisPool jedisPool;
	
	/**
	 * 获取redis连接池
	 * @return
	 */
	public JedisPool getJedisPool(){
		return jedisPool;
	}
	/**
	 * 设置redis连接池
	 */
	public void setJedisPool(JedisPoolWriper jedisPoolWriper){
		this.jedisPool=jedisPoolWriper.getJedisPool();
	}
	/**
	 * 从jedis连接池获取jedis对象
	 * @return 
	 */
	public Jedis getJedis(){
		return jedisPool.getResource();
	}
	///////////////////////////////
	
	public class Keys{
	/**
	 * 清空所有key	
	 */
		public String flushAll() {
			Jedis jedis = getJedis();
			String stata = jedis.flushAll();
			jedis.close();
			return stata;
		}
	/**
	 * 删除keys对应的记录，可以是多个key
	 */
		public long del(String... keys){
			Jedis jedis=getJedis();
			long count=jedis.del(keys);
			jedis.close();
			return count;
		}
		
	/**
	 * 判断key是否存在
	 * 
	 */
		
		public  boolean exits(String key){
			Jedis jedis=getJedis();
			boolean exit=jedis.exists(key);
			jedis.close();
			return exit;
		}
		
	/**
	 * 查找所有匹配给定的模式的值
	 * key的表达式:*表示多个，?表示一个，例如shopcategory*
	 */
	  public Set<String> keys(String pattern){
		  Jedis jedis=getJedis();
		  Set<String> set=jedis.keys(pattern);
		  jedis.close();
		  return set;
	  }
	}
	
	public class Strings{
		/**
		 * 根据key获取记录
		 */
		public  String get(String key){
			Jedis jedis=getJedis();
			String value=jedis.get(key);
			jedis.close();
			return value;
		}
		/**
		 * 添加记录，如果记录存在将覆盖原有的value
		 * @return状态码
		 */
		public String set(String key,String value){
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}
		/**
		 * @param byte[]
		 * key
		 * @param
		 * value
		 * 添加记录，如果记录存在将覆盖原有的value
		 * @return状态码
		 */
		private String set(byte[] key, byte[] value) {
			Jedis jedis=getJedis();
			String status=jedis.set(key, value);
			jedis.close();
			return status;
		}
		
	}
	
	
	
}


