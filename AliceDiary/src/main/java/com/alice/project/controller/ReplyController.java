package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.service.ReplyService;

@Controller
@RequestMapping("/AliceDiary")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;

	@PostMapping("/community/get/reply")
	@ResponseBody
	public void replyWrite(String memberId, Long postNum, String content) {
		
		replyService.replyWrite(memberId, postNum, content);
		
	}

}
