package com.alice.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.service.FriendService;
import com.alice.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FriendsController {

	private final FriendService friendService;
	private final MemberService memberService;

	// 친구 추가(회원 id검색)
	@PostMapping("/friends/add")
	public String addFriend(String searchId) {

		friendService.addFriendship(1L, searchId);
		return "redirect:/friends";
	}

	// 추가된 친구 목록 조회
	@GetMapping("/friends")
	public String friendshiplist(Model model) {
		Long adderNum = 1L;

		List<Friend> friendList = friendService.friendship(adderNum);

		List<Member> friendship = new ArrayList<>();
		
		for (Friend f : friendList) {
			friendship.add(memberService.findByNum(f.getAddeeNum()));
		}

		model.addAttribute("friendList", friendship);
		return "friends/friendslist";
	}

	
	// 친구 검색해서 추가
	@PostMapping("/friends/searchMember")
	@ResponseBody
	public Member searchMember(String id) {
		return friendService.searchMember(id);
	}

	// 페이징 처리
}
