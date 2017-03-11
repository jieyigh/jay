package com.ssc.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		System.out.println("服务器开始启动..");
		if (ApplicationServer.getInstance() != null) {
			System.out.println("服务器启动成功");
		} else {
			System.out.println("服务器启动失败");
		}
		
	}

	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
		System.out.println("服务器释放资源关闭");
		ApplicationServer.releaseInstance();
	}

}
