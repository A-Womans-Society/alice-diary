package com.alice.project.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Member;
import com.alice.project.service.MemberService;
import com.alice.project.web.MemberDto;

import lombok.RequiredArgsConstructor;

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

	// 회원가입 후 메인페이지로 이동
	@PostMapping(value = "/register")
	public String memberForm(@Valid MemberDto memberDto, BindingResult bindingResult, Model model,
			HttpSession session) {
		logger.info("POST 나옴");

		if (bindingResult.hasErrors()) {
			return "login/registerForm";
		}
		if (!memberDto.getProfileImg().getOriginalFilename().equals("")) {
			String originName = memberDto.getProfileImg().getOriginalFilename();
			String saveName = memberDto.getId() + "." + originName.split("\\.")[1];
			String savePath = session.getServletContext().getRealPath("c:\\Temp\\upload");

			try {
				memberDto.getProfileImg().transferTo(new File(savePath, saveName));
				memberDto.setSaveName(saveName);

				Member member = Member.createMember(memberDto, passwordEncoder);
				memberService.saveMember(member);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/";
	}
	

	@PostMapping("/register/idCheck")
    @ResponseBody
	public int checkIdDuplication(@RequestParam(value = "id") String id) {
		logger.info("userIdCheck 진입");
        logger.info("전달받은 id:"+id);
	    int check = memberService.checkIdDuplicate(id);
	    logger.info("확인 결과:"+check);
	    return check;
	}
}
