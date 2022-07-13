package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Member;
import com.alice.project.service.FriendService;
import com.alice.project.web.FriendshipDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FriendsController {

	private final FriendService friendService;

	// 친구 추가
	@PostMapping("/friends/add")
	public String addFriend(String searchId) {
		
		friendService.addFriendship(1L, searchId);
		return "redirect:/friends";
		/*1
		 * // 사용자 번호 세션에서 가져오기 // Long adderNum = (Long)session.getAttribute("num");
		 * Long adderNum = 1L; // 추가할 회원번호 가져오기 Long addeeNum = fdto.getAddeeNum(); //
		 * 친구테이블에 관계 만들어주기 friendService.addFriendship(adderNum, addeeNum); // 다시 친구목록
		 * 보여주기(서비스 이동) return "friends/friendslist";
		 */
		
		
	}

	// 추가된 친구 목록 조회
	@GetMapping("/friends")
	public String Friendship(Model model) {
		// List<FriendshipDto> friendship = friendService.findAll();
		model.addAttribute("friendshipDto", new FriendshipDto());
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
