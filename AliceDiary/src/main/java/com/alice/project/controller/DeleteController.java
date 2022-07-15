package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.service.DeleteService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/AliceDiary")
@Slf4j
public class DeleteController {
	
	@Autowired
	private DeleteService deleteService;
	

	
	@RequestMapping("/community/delete")
	public String postDelete(Long num) {
		System.out.println("컨트롤러 실행 num:"+num);
		
		deleteService.deletePostwithFile(num);			
			
		deleteService.deletePost(num);			
		
		return "redirect:list";
	}
	
	@PostMapping("/community/put/filedelete")
	@ResponseBody
	public Integer oneFileDelete(Long num) {
		log.info("!!!!!!! file num : " + num);
		return deleteService.deleteOneFile(num);
	}

}
