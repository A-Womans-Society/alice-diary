package com.alice.project.controller;
import java.util.List;

//웹에서 온 요청에서 데이터를 받아 서비스로 넘겨줌
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alice.project.domain.Member;
import com.alice.project.service.FriendsListService;

@Controller
public class FriendsListController{
	// @dddMapping(value= “도메인에서 검색할 주소”)
	// return은 jsp 파일로 이동
	// submit은 action으로 이동해서 add와 매칭되는 컨트롤러로 가서 메서드 실행	
	@Autowired
	FriendsListService friendslistService;
	
	@GetMapping(value ="/AliceDiary/Friends")
	public String friendsList(Model model) {
		List<Member> friends = friendslistService.findAll();
		model.addAttribute("friends", friends);
		return "/templates/friendslist";
	}

}
