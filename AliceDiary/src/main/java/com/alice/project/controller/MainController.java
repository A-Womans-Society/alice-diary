package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;


@Controller
public class MainController {

	//main 페이지로
	@GetMapping(value = "/")
	public String main(@RequestParam(value = "error", required = false)String error,                        
						@RequestParam(value = "exception", required = false)String exception,                       
						Model model) {
		model.addAttribute("error", error);        
		model.addAttribute("exception", exception);
		
		return "index";
	}
}