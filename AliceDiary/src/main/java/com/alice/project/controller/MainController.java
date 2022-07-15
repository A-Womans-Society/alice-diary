package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {

	//main 페이지로
	@GetMapping(value = "/")
	public String main() {
		return "index";
	}
}