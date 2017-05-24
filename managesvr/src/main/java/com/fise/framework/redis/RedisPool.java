package com.fise.framework.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fise.framework.config.ConfigProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-5
 * @desc Redis资源池
 */
public class RedisPool {
	private JedisPool jedisPool;
	private JedisPoolConfig jedisPoolConfig;
	private String host;
	private int port;
	private int db;
	private String password;
	private int timeout = 1000;
	Properties redisProperties;
	
	public RedisPool (String host, int port, int db) {
		this.host = host;
		this.port = port;
		this.db = db;
		
		jedisPool = null;
		jedisPoolConfig = null;
		// redisProperties = RedisManager.getInstance().getRedisProperties();
		redisProperties = ConfigProperties.getRedisProperties();
	}
	
	
	// 初始化pool配置
	private void initJedisPoolConfig() {
		
		password = redisProperties.getProperty("redis_password");
		timeout = Integer.parseInt(redisProperties.getProperty("redis_timeout"));
		boolean isTestOnBorrow = false;
		boolean isTestWhileIdle = false;
		if ("true".equals(redisProperties.getProperty("redis_test_on_borrow"))) {
			isTestOnBorrow = true;
		}
		if ("true".equals(redisProperties.getProperty("redis_test_on_borrow"))) {
			isTestOnBorrow = true;
		}
		int maxTotal = Integer.parseInt(redisProperties.getProperty("redis_max_total"));
		int maxIdle = Integer.parseInt(redisProperties.getProperty("redis_max_idle"));
		int minIdle = Integer.parseInt(redisProperties.getProperty("redis_min_idle"));
		int maxWait = Integer.parseInt(redisProperties.getProperty("redis_max_wait"));
		
		if (jedisPoolConfig == null) {
			jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setTestOnBorrow(isTestOnBorrow); 		// 从数据库连接池中取得连接时，对其的有效性进行检查
			jedisPoolConfig.setMaxTotal(maxTotal);					// 最大连接数
			jedisPoolConfig.setMaxIdle(maxIdle); 					// 最大闲置的连接数
			jedisPoolConfig.setMinIdle(minIdle); 					// 最少
			jedisPoolConfig.setMaxWaitMillis(maxWait); 				// 请求最长等待时间/毫秒
			jedisPoolConfig.setMinEvictableIdleTimeMillis(-1);		// 已建立的连接不回收，高并发时建立连接会很耗资源
			jedisPoolConfig.setTestWhileIdle(isTestWhileIdle); 		// 闲置时测试
		}
	}
	
	// 启动
	public void launch() {
		if (jedisPool == null) {
			initJedisPoolConfig();
			jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		}
	}
	
	// 销毁
	public void destory() {
		if(jedisPool != null) {
			jedisPool.destroy();
		}
	}
	
	// 获取连接
	public Jedis getResource() {
		Jedis jedisResource = null;
		if(jedisPool != null) {
			jedisResource = jedisPool.getResource();
			if(db > 0) {
				jedisResource.select(db);
			}
		}
		return jedisResource;
	}
	
	// 释放连接
	public void returnResource(Jedis jedisResource) {
		jedisPool.returnResource(jedisResource);
	}
}
