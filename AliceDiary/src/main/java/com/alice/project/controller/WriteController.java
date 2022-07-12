package com.alice.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.domain.Post;
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

	@GetMapping("/community/post")
	public String writeform(Model model) {
		System.out.println("get");
		model.addAttribute("writeFormDto", new WriteFormDto());
		return "community/writeForm";
	}

	@PostMapping("/community/post")
	public String writeSubmit(WriteFormDto writeFormDto, HttpSession session) {
		System.out.println("service 도착");
		Post post = Post.createPost(writeFormDto);
		System.out.println("post 객세생성");
		writeService.write(post);

		fileUploadService.postFileUpload(writeFormDto.getOriginName(), session);
		
		System.out.println("service 이동");
		return "redirect:list";
	}

}
