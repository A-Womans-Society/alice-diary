package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.service.ViewService;

@Controller
@RequestMapping("/AliceDiary")
public class ViewController {

	@Autowired
	private ViewService viewService;
	
	/*
	 * @GetMapping("community/get") public String postView(Model model, Long num) {
	 * 
	 * model.addAttribute("postView",viewService.postView(num)); return
	 * "community/postView";
	 * 
	 * }
	 */
}
