package com.alice.project.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.Member;
import com.alice.project.service.MemberService;
import com.alice.project.web.MemberDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/AliceDiary")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);


	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping(value = "/register")
	public String memberForm(Model model) {
		logger.info("GET 나옴");
		model.addAttribute("memberDto", new MemberDto());
		return "login/registerForm";
	}

	
	//회원가입 후 메인페이지로 이동
	@PostMapping(value="/register")
	public String memberForm(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
		logger.info("POST 나옴");
		if(bindingResult.hasErrors()) {
			return "login/registerForm";
		}
		try {
			Member member = Member.createUser(memberDto, passwordEncoder);
			memberService.saveMember(member);
		}catch(IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "login/registerForm";
		}
		return "redirect:/";
	}
}
