package com.alice.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.service.FriendService;
import com.alice.project.service.FriendsGroupService;
import com.alice.project.service.MemberService;
import com.alice.project.service.ProfileService;
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
	private final ProfileService profileService;

	// 친구 추가(회원 id검색)
	@PostMapping("/friends/add")
	public String addFriend(String searchId, @AuthenticationPrincipal UserDetails user) {
		Member m = memberService.findById(user.getUsername());
		log.info("member : " + m.getId());
		log.info("member : " + searchId);
		friendService.addFriendship(m, searchId);
		return "redirect:/friends";
	}

	// 친구 검색해서 추가
	@PostMapping("/friends/searchMember")
	@ResponseBody
	public Member searchMember(String id, @AuthenticationPrincipal UserDetails user) {
		return friendService.searchMember(id);
	}

	// 추가된 친구 목록 조회
	@GetMapping("/friends")
	public String friendshiplist(Model model, HttpSession session, @AuthenticationPrincipal UserDetails user) {
		// String userNum = session.getAttribute("userNum");
		Long adderNum = memberService.findById(user.getUsername()).getNum();

		List<Friend> friendList = friendService.friendship(adderNum);
		List<FriendshipDto> friendship = new ArrayList<>();

		for (Friend f : friendList) {
			Member m = memberService.findByNum(f.getAddeeNum());
			String groupName = friendsGroupService.getGroupName(f.getGroupNum());
			FriendshipDto dto = new FriendshipDto(m.getNum(), m.getId(), m.getName(), m.getMobile(), m.getBirth(),
					m.getGender(), m.getEmail(), groupName);

			friendship.add(dto);
		}
		model.addAttribute("friendList", friendship);
		model.addAttribute("member", memberService.findById(user.getUsername()));
		return "friends/friendslist";
	}

	// 친구 목록에서 친구 검색
	@PostMapping("/friends/searchFriend")
	@ResponseBody
	public List<FriendshipDto> searchFriend(String friends, @AuthenticationPrincipal UserDetails user) {
		Long adderNum = memberService.findById(user.getUsername()).getNum();
		List<Member> searchFriend = friendService.searchFriend(friends, adderNum);
		List<FriendshipDto> searchFriendList = new ArrayList<FriendshipDto>();
		for (Member f : searchFriend) {
			Member sf = memberService.findByNum(f.getNum());
			Friend fg = friendService.groupNum(adderNum, f.getNum());
			String groupName = friendsGroupService.getGroupName(fg.getGroupNum());
			log.info("그룹이름:" + groupName);
			FriendshipDto dto = new FriendshipDto(sf.getNum(), sf.getId(), sf.getName(), sf.getMobile(), sf.getBirth(),
					sf.getGender(), sf.getEmail(), groupName);
			searchFriendList.add(dto);
		}
		return searchFriendList;
	}

	// 친구 프로필 상세보기
	@GetMapping("/friends/friendInfo/{id}")
	public String friendInfo(Model model, @PathVariable("id") String id, @AuthenticationPrincipal UserDetails user) {
		Member member = profileService.findById(id);
		log.info("member=" + member);
		model.addAttribute("friend", member);
		model.addAttribute("member", memberService.findById(user.getUsername()));
		return "friends/friendInfo";
	}
}