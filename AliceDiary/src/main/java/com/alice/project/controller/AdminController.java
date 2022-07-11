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
		return "admin/memberList";
	}
	
	/* 회원 삭제 */
	@PostMapping(value = "/member/{memberNum}")
	public String deleteMember(@PathVariable("memberNum") Long memberNum, Model model) {
		
		memberService.deleteOne(memberNum);
		model.addAttribute("message", "정상적으로 처리되었습니다.");
	  return "redirect:/member";
	}
	
	
	
	

}
