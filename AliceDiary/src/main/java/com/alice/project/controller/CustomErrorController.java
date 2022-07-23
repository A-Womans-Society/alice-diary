package com.alice.project.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

//	@Override
//	public String getErrorPath() {
//		return null;
//	}

	@GetMapping(value = "/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); /// error로 들어온 에러의 status 불러오기

		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error/404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error/403";
			} else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
				return "error/502";
			} else {
				return "error/error";
			}
		}
		return "error/error";
	}

}
