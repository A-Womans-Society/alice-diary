package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.repository.FriendSearchRepository;
import com.alice.project.web.FriendsDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendSearchService {
	
	private final FriendSearchRepository friendsSearchRepository;
	
	// 친구 아이디 검색
	public List<FriendsDto> findId(String memId){
		return friendsSearchRepository.findBymemId(memId);
	}
	// 친구 전화번호 검색
	public List<FriendsDto> findMobile(String mobile){
		return friendsSearchRepository.findByMobile(mobile);
	}
	// 친구 이름 검색
	public List<FriendsDto> findName(String name){
		return friendsSearchRepository.findByname(name);
	}

}
