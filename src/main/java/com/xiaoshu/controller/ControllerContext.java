package com.xiaoshu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerContext {

	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	public static void set(HttpServletRequest request, HttpServletResponse response) {
		ControllerContext.request.set(request);
		ControllerContext.response.set(response);
	}

	public static HttpServletRequest getRequest() {
		return request.get();
	}

	public static HttpServletResponse getResponse() {
		return response.get();
	}

}
