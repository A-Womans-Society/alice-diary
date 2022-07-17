package com.alice.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.repository.FriendRepository;
import com.alice.project.service.FriendService;
import com.alice.project.service.FriendsGroupService;
import com.alice.project.service.MemberService;
import com.alice.project.web.FriendshipDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FriendsController {

	private final FriendService friendService;
	private final MemberService memberService;
	private final FriendsGroupService friendsGroupService;

	// 친구 추가(회원 id검색)
	@PostMapping("/friends/add")
	public String addFriend(String searchId) {
		// adderNum을 4로 하드코딩해놓은 것
		friendService.addFriendship(5L, searchId);
		return "redirect:/friends";
	}

	// 친구 검색해서 추가
	@PostMapping("/friends/searchMember")
	@ResponseBody
	public Member searchMember(String id) {
		return friendService.searchMember(id);
	}

	// 추가된 친구 목록 조회
	@GetMapping("/friends")
	public String friendshiplist(Model model, HttpSession session) {
		// String userNum = session.getAttribute("userNum");
		Long adderNum = 5L; // 세션에서 받아서 넣어야 함 지금은 5번 회원으로 하드코딩

		List<Friend> friendList = friendService.friendship(adderNum);
		List<FriendshipDto> friendship = new ArrayList<>();

		for (Friend f : friendList) {
			Member m = memberService.findByNum(f.getAddeeNum());
			String groupName = friendsGroupService.getGroupName(f.getGroupNum());
			FriendshipDto dto = new FriendshipDto(m.getNum(),
					m.getId(), m.getName(), m.getMobile(), m.getBirth(),
					m.getGender(), m.getEmail(), groupName);

			friendship.add(dto);
		}
		model.addAttribute("friendList", friendship);
		return "friends/friendslist";
	}

	// 친구 목록에서 친구 검색
	@PostMapping("/friends/searchFriend")
	@ResponseBody
	public List<FriendshipDto> searchFriend(String friends, Long adderNum, Model model) {
		List<Member> searchFriend = friendService.searchFriend(friends, adderNum);
		List<FriendshipDto> searchFriend1 = new ArrayList<FriendshipDto>();
		for (Member f : searchFriend ) {
			Member sf = memberService.findByNum(f.getNum());
			Friend friend =friendService.groupNum(f.getNum());
			String groupName = friendsGroupService.getGroupName(friend.getGroupNum());
			log.info("그룹이름:" + groupName);
			FriendshipDto dto = new FriendshipDto(sf.getNum(),
					sf.getId(), sf.getName(), sf.getMobile(), sf.getBirth(),
					sf.getGender(), sf.getEmail(), groupName);
			log.info("dto: " +dto);
			searchFriend1.add(dto);
		}
		model.addAttribute("friendList", searchFriend1);
		
		return searchFriend1;
	}
}
