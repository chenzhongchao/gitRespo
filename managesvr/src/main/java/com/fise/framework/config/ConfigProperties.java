package com.fise.framework.config;  
  
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {  
	
	private static final Properties properties = new Properties();;
	private static final Properties redisProperties = new Properties();
	
    static {  
    	try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			redisProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
    
    public static String getValue(String key) {
    	return properties.getProperty(key);
    }
   
    public static Properties getRedisProperties() {
    	return redisProperties;
    }
}