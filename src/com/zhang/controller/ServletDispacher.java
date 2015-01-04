package com.zhang.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhang.util.ClassOperation;
import com.zhang.util.ConstantUtil;

@SuppressWarnings("unchecked")
public class ServletDispacher extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Map<String, Class<?>> routes;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		ServletContext context = getServletConfig().getServletContext();
		routes = (Map<String, Class<?>>) context.getAttribute(ConstantUtil.routeName);
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		int pos = requestURI.indexOf(contextPath);
		if (pos != -1) {
			requestURI = requestURI.substring(pos + contextPath.length() + 1);
		}
		String[] urlParts = requestURI.split(ConstantUtil.splitSlash);
		String controllerName = ConstantUtil.defaultControllerClassName;;
		String methodName = ConstantUtil.defaultMethodName;
        List<String> remainParts = null;
		if (urlParts == null) {
			;
		} else if (urlParts.length == 1) {
		    controllerName = urlParts[0]; 
		} else {
			controllerName = urlParts[0];
			methodName = urlParts[1];
			if (urlParts.length >= 3) {
				remainParts = new ArrayList<String>();
				for (int i = 2; i < urlParts.length; ++i) {
					remainParts.add(urlParts[i]);
				}
			}
		}
		
		Iterator<String> iter = routes.keySet().iterator();
		Class<?> classObj = null;
		
		while (iter.hasNext()) {
			String className = iter.next();
			if (className.equals(controllerName)) {
				classObj = routes.get(className);
			}
		}
		
		if (classObj == null) {
			System.err.println("Can\'t find class " + controllerName);
			return;
		}
		
		try {
			ClassOperation.runMethod(classObj, methodName, requestMethod, remainParts, request, response);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
