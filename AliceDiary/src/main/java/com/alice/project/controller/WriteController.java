package com.alice.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.service.FileUploadService;
import com.alice.project.service.WriteService;
import com.alice.project.web.WriteFormDto;

@Controller
@RequestMapping("/AliceDiary")
public class WriteController {

	@Autowired
	private WriteService writeService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping("/community/write")
	public String writeform() {
		System.out.println("get");
		
		return "community/writeForm";
	}

	@PostMapping("/community/write")
	public String writeSubmit(WriteFormDto writeFormDto, List<MultipartFile> files, HttpSession session) {
	
		
		Long memberNum = (Long) session.getAttribute("member_num");
		
		writeService.write(writeFormDto, memberNum);
		
		fileUploadService.postFileUpload(files, session);
		return "community/list";
	}

}
