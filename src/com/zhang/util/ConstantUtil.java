package com.zhang.util;

public class ConstantUtil {
	
	public static final String configPath = "/WEB-INF/classes/config/controller.properties";
	
	public static final String routeName = "routes";
	
	public static final String decodeCode = "UTF-8";
	
	public static final String splitSlash = "/";
	
	public static final String defaultControllerClassName = "IndexController";
	public static final String defaultMethodName = "index";
	
	public static class RequestMethod {
		public static final String POST = "POST";
		public static final String GET = "GET";
	}
	
	public static class MethodPrefix {
		public static final String POST = "post";
		public static final String GET = "get";
	}
	
	public static class ControllerConfig {
		public static final String ControllerPathName = "controller.path";
	}

}
