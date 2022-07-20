package com.alice.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alice.project.domain.Member;
import com.alice.project.domain.Suggestion;
import com.alice.project.service.MemberService;
import com.alice.project.service.SuggestionService;
import com.alice.project.web.SuggestionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LeaveController {

	private final MemberService memberService;
	private final SuggestionService suggestionService;

	
	@GetMapping(value = "/member/{id}/leave")
	public String memberLeave(@PathVariable String id, Model model) {
		log.info("GET 진입");
		Member member = memberService.findById(id);
		model.addAttribute("member", member);
		model.addAttribute("suggestionDto", new SuggestionDto());
		return "/leave/memberLeave";
	}
	
	@PostMapping(value = "/member/{id}/leave")
	public String memberLeave(@PathVariable String id, SuggestionDto suggestionDto, RedirectAttributes re, Model model, HttpSession session) {
		log.info("POST 진입");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Member member = memberService.findById(id);
		Suggestion suggestion = Suggestion.createSuggestion(suggestionDto, member);
		suggestionService.saveSuggest(suggestion); //건의 사항 insert
		if(!encoder.matches(suggestionDto.getPassword(), member.getPassword())) {
			log.info("현재 비밀번호 에러");
			model.addAttribute("msg", "현재 비밀번호가 일치하지 않습니다.");
			return "redirect:/member/" + id + "/leave";
		}else {
			member = Member.changeMemberOut(member); //sataus USER_OUT으로 바꾸기
			memberService.saveMember(member); //update
			Member.changeMemberOutId(member); //아이디 (알수없음)으로 수정, DB에는 반영X
			log.info("member status == "+member.getStatus());
			log.info("member id == "+member.getId());
		}
		return "redirect:/logout";
	}
}
