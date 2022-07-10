package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/AliceDiary")
public class AliceController {
	@GetMapping("/alice")
	public String calendar() {
		return "alice/calendar";
	}
}
