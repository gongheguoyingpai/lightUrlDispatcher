package com.zhang.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchPackageClasses {
	
	public static Map<String, Class<?>> getClasses(String pack) {
	    Map<String, Class<?>> classes = new LinkedHashMap<String, Class<?>>();
	    boolean recursive = true;
	    String packageName = pack;
	    String packageDirName = packageName.replace('.', '/');
	    Enumeration<URL> dirs;
	    try {
	        dirs = Thread.currentThread().getContextClassLoader().getResources(
	                packageDirName);
	        while (dirs.hasMoreElements()) {
	            URL url = dirs.nextElement();
	            String protocol = url.getProtocol();
	            if ("file".equals(protocol)) {
	                String filePath = URLDecoder.decode(url.getFile(), ConstantUtil.decodeCode);
	                findAndAddClassesInPackageByFile(packageName, filePath,
	                        recursive, classes);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return classes;
	}
	
	
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Map<String, Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					classes.put(className.toLowerCase(), Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
