package com.fise.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fise.framework.elasticsearch.ElasticSearchManager;
import com.fise.framework.redis.RedisManager;

public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// 关闭Redis pool连接
		RedisManager.getInstance().shutDown();
				
		// 关闭ElasticSearch TransportClient
		ElasticSearchManager.getInstance().shutDown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 实例化RedisManager 
		RedisManager.getInstance();
				
		// 实例化ElasticSearchManager
		ElasticSearchManager.getInstance();
	}

}
