package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alice.project.domain.Member;
import com.alice.project.repository.ProfileRepository;
import com.alice.project.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

	private final ProfileService profileService;

	
	@GetMapping(value = "/member/{id}")
	public String myProfile(@PathVariable String id, Model model) {
		Member member = profileService.findById(id);
		log.info("member=" + member);
		model.addAttribute("member", member);
		return "profile/myProfile";
	}
	
	@PostMapping(value="/member/{id}")
	public String updateProfile(@PathVariable String id, Model model) {
		log.info("POST 진입!!");
		Member member = profileService.findById(id);
		model.addAttribute("member", member);
		return "profile/updateProfile";
	}
}
