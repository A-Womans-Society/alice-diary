package com.alice.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.MemberService;
import com.alice.project.service.WriteService;
import com.alice.project.web.WriteFormDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WriteController {

	@Autowired
	private WriteService writeService;

	@Autowired
	private AttachedFileService attachedFileService;
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/community/post")
	public String writeform(Model model) {
		log.info("get");
		model.addAttribute("writeFormDto", new WriteFormDto());
		return "community/writeForm";
	}

	@PostMapping("/community/post")
	public String writeSubmit(WriteFormDto writeFormDto, HttpSession session, @AuthenticationPrincipal UserDetails user) {
		log.info("controller 실행");
		
		Member member = memberService.findById(user.getUsername());

		
		Post post = Post.createPost(writeFormDto, member);
		writeService.write(post);

		attachedFileService.postFileUpload(writeFormDto.getOriginName(), writeService.write(post), session, user.getUsername());

		return "redirect:list";
	}

	
}
