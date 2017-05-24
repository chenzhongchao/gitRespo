package com.fise.framework.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.fise.framework.config.ConfigProperties;

import redis.clients.jedis.Jedis;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-5
 * @desc Redis管理器，spring启动完毕后加载（配置web.xml）
 */
public class RedisManager {
	private ConcurrentHashMap<String, RedisPool> redisPoolMap = new ConcurrentHashMap<String, RedisPool>();
	
	// 启动标志
	private boolean isLanuch;
	
	// redis.properties 配置文件
	private Properties redisProperties;
	
	private volatile static RedisManager Instance;
	
	// DCL 创建单例
	public static RedisManager getInstance() {
		if (Instance == null) {
			synchronized (RedisManager.class) {
				if (Instance == null) {
					Instance = new RedisManager();
				}
			}
		}
		
		return Instance;
	}
	
	private RedisManager() {
		/*redisProperties = new Properties();
		isLanuch = false;
		loadReisConf();
		launch();
		*/
		
		redisProperties = ConfigProperties.getRedisProperties();
		isLanuch = false;
		launch();
	}
	
	// 加载redis.properties
	private void loadReisConf() {
		//InputStream propertiesFileStream = this.getClass().getResourceAsStream("/redis.properties");
		try {
			//redisProperties.load(propertiesFileStream);
			redisProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/redis.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 
	public Properties getRedisProperties() {
		return redisProperties;
	}
	
	// 启动
	private void launch() {
		if (!isLanuch) {
			String needLaunchPoolInstance = redisProperties.getProperty("instances");
			String[] needLaunchPools = needLaunchPoolInstance.split(",");
			for(String instance : needLaunchPools) {
				String redisHost = redisProperties.getProperty(instance + "_host");
				Integer redisPort = Integer.valueOf(redisProperties.getProperty(instance + "_port"));
				Integer redisDB = Integer.valueOf(redisProperties.getProperty(instance + "_db"));
				
				//实例化，启动，并塞入Hashmap中
				RedisPool redisPoolInstance = new RedisPool(redisHost, redisPort, redisDB);
				redisPoolInstance.launch();
				redisPoolMap.put(instance, redisPoolInstance);
			}
			isLanuch = true;
		}
	}
	
	// 关闭
	public void shutDown() {
		if(redisPoolMap.size() > 0) {
			Iterator<Entry<String, RedisPool>> iterator = this.redisPoolMap.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, RedisPool> hashEntry = iterator.next();
				RedisPool poolInstance = hashEntry.getValue();
				poolInstance.destory();
				
				redisPoolMap.remove(hashEntry.getKey());
			}
			redisPoolMap.clear();
		}
	}
	
	// 从指定的链接池中获取一个链接
	public Jedis getResource(String poolName) {
		Jedis jedisResource = null;
		RedisPool redisPool =  redisPoolMap.get(poolName);
		if (redisPool != null) {
			jedisResource = redisPool.getResource();
		}
		
		return jedisResource;
	}
	
	// 释放一个连接
	public void returnResource(String poolName,  Jedis jedisResource) {
		if(jedisResource == null) {
			return ;
		}
		
		RedisPool redisPool = redisPoolMap.get(poolName);
		if (redisPool != null) {
			redisPool.returnResource(jedisResource);
		}
		
		return ;
	}
}
