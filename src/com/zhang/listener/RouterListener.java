package com.zhang.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zhang.util.ConfigUtil;
import com.zhang.util.ConstantUtil;
import com.zhang.util.SearchPackageClasses;


public class RouterListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.err.println("Listener will start");
		ServletContext context = sce.getServletContext();
		
		ConfigUtil.readConfig(context.getRealPath(ConstantUtil.configPath));
		
		String controllerPath = ConfigUtil.getConfigItem(ConstantUtil.ControllerConfig.ControllerPathName);
		
		if (controllerPath == null) {
			System.err.println(ConstantUtil.ControllerConfig.ControllerPathName + " must be given!");
			System.err.println("Check you config file at " + ConstantUtil.configPath);
			System.exit(1);
		}
		
		Map<String, Class<?>> routes = SearchPackageClasses.getClasses(controllerPath);
		context.setAttribute(ConstantUtil.routeName, routes);
		
		System.err.println("Listener has initialized!");
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		System.err.println("Listener will be destroyed!");
	}
}
