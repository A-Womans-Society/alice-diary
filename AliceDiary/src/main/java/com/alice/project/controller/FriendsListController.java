package com.alice.project.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alice.project.domain.Member;
import com.alice.project.service.FriendsListService;
import com.alice.project.web.FriendsDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FriendsListController{
	
	private final FriendsListService friendslistService;
	// 전체 조회
	@GetMapping(value ="/AliceDiary/friends")
	public String friendsList(Model model) {
		List<FriendsDto> friends = friendslistService.findAll();
		model.addAttribute("friends", friends);
		return "/friendslist";
	}
	
	
	
}
