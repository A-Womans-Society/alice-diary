package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.service.AttachedFileService;
import com.alice.project.service.DeleteService;

@Controller
public class DeleteController {
	
	@Autowired
	private DeleteService deleteService;
	
	@Autowired
	private AttachedFileService attachedFileService;
	
	@RequestMapping("/AliceDiary/community/delete")
	public String postDelete(Long num) {
		System.out.println("num:"+num);
		
		attachedFileService.deletePostwithFile(num);
		
		deleteService.deletePost(num);
		
		return "redirect:list";
	}
	

}
