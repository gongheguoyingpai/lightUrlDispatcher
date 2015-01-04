package com.zhang.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClassOperation {
	
	@SuppressWarnings("unchecked")
	public static void runMethod(@SuppressWarnings("rawtypes") Class classObj, String methodName, String requestMethod, List<String> urlParts, 
			    HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if (methodName == null) {
			methodName = "index";
		}
		
		if (methodExists(classObj, methodName) == false) {
			if (requestMethod.equals(ConstantUtil.RequestMethod.POST)) {
				methodName = ConstantUtil.MethodPrefix.POST + methodName;
			} else {
				methodName = ConstantUtil.MethodPrefix.GET + methodName;
			}
			if (methodExists(classObj, methodName) == false) {
				throw new NoSuchMethodException();
			}
		}
		
		Method method = null;
		
		if (urlParts == null) {
			method = classObj.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		    method.invoke(classObj.newInstance(), request, response);
		} else if (urlParts.size() == 1) {
			method = classObj.getMethod(methodName, String.class, HttpServletRequest.class, HttpServletResponse.class);
		    method.invoke(classObj.newInstance(), urlParts.get(0), request, response);
		} else {
			method = classObj.getMethod(methodName, List.class, HttpServletRequest.class, HttpServletResponse.class);
		    method.invoke(classObj.newInstance(), urlParts, request, response);
		}
	}
	
	
	public static boolean methodExists(@SuppressWarnings("rawtypes") Class classObj, String methodName) {
		Method[] methods = classObj.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

}
