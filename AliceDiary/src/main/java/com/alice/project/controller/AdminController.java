package com.alice.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "/admin/memberList";
	}
	
	/* 회원 정보 상세보기 */
	@GetMapping(value = "/member/{num}")
	public String showMemberOne(@PathVariable("num") Long num, Model model) {
		Member member = memberService.findOne(num);
		model.addAttribute("member", member);
	  return "/admin/memberDetail";
	}	
	
	/* 회원 내보내기 */
	@DeleteMapping(value = "/member/{num}")
	@ResponseBody
	public int changeMemberOut(@PathVariable("num") Long num, Model model) {
		int result = memberService.deleteOne(num);
		log.info("**********result : " + result);
		return result;
	}
	
	/* 회원 복구하기 */
	@PatchMapping(value = "/member/{num}")
	@ResponseBody
	public int changeMemberIn(@PathVariable("num") Long num, Model model) {
		int result = memberService.returnOne(num);
		log.info("**********result : " + result);
		return result;
	}
	
	
	
	

}
