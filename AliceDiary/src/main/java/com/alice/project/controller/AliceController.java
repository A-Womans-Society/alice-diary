package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alice")
public class AliceController {
	@GetMapping
	public String calendar() {
		return "alice/calendar";
	}
}
