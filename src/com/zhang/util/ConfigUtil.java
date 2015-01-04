package com.zhang.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

	private static Properties properties;
	
	public static void readConfig(String path) {
		try {
	    	InputStream in = new BufferedInputStream(new FileInputStream(path));
	    	properties = new Properties();
	    	properties.load(in);
	    } catch (IOException e) {
	    	System.err.println("读取配置文件出错!");
	    }
	}
	
	public static String getConfigItem(String key) {
		return properties.getProperty(key);
	}
}
