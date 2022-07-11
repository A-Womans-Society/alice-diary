package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.repository.FriendsListRepository;
import com.alice.project.web.FriendsDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendsListService {

	private final FriendsListRepository friendslistRepository;

	// 회원 전체 조회
	public List<FriendsDto> findAll() {
		return friendslistRepository.findAll();
	}

	// 개별 회원 조회
	public Member findOne(String id) {
		return friendslistRepository.findMemberByid(id);
	}

}
