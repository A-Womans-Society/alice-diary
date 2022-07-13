package com.alice.project.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.ViewService;

@Controller
@RequestMapping("/AliceDiary")
public class ViewController {

	@Autowired
	private ViewService viewService;
	
	@Autowired
	private AttachedFileService attachedFileService;
	
	
	 @GetMapping("community/get") 
	 public String postView(Model model, Long num,Pageable pageable) {
		 
		 System.out.println("num :"+num);
		 
		 Post viewPost = viewService.postView(num);
		 
		 viewService.viewCntUp(num);
		 
	  model.addAttribute("postView",viewPost); 
	  
	 List<AttachedFile> files = attachedFileService.fileView(viewPost,pageable);
	  model.addAttribute("files",files); 
	  
	 
	  return "community/postView";  
	  }
	 
	 @GetMapping("community/download/{num}")
	 public ResponseEntity<UrlResource> fileDownload(@PathVariable("num") Long num) throws MalformedURLException, UnsupportedEncodingException {
	     
		 
	     return attachedFileService.postFileDownload(num);
	 }
	     
	
}
