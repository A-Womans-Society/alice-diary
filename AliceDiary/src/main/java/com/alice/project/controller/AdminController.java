package com.alice.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.Member;
import com.alice.project.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final MemberService memberService;
	
	/* 회원 목록 */
	@GetMapping(value = "/member")
	public String showMemberList(Model model) {
		log.info("showMemberList 컨트롤러 실행");
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "/admin/memberList";
	}
	
	/* 회원 정보 상세보기 */
	@GetMapping(value = "/member/{num}")
	public String showMemberOne(@PathVariable("num") Long num, Model model) {
		log.info("회원 상세보기 컨트롤러 실행!");
		Member member = memberService.findOne(num);
//		model.addAttribute("message", "정상적으로 처리되었습니다.");
		model.addAttribute("member", member);
	  return "/admin/memberDetail";
	}	
	
	/* 회원 내보내기 */
	@PostMapping(value = "/member/{num}")
	public String changeMemberOut(@PathVariable("num") Long num, Model model) {
		log.info("회원 내보내기 컨트롤러 실행!");
		memberService.deleteOne(num);
		model.addAttribute("message", "정상적으로 처리되었습니다.");
	  return "redirect:/admin/member";
	}
	
	
	
	

}
