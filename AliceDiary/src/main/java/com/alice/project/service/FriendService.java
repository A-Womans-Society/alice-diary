package com.alice.project.service;

import java.util.List;
import java.util.Optional;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.repository.FriendRepository;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;

	// 친구추가 서비스(추가하는 멤버회원 번호, 추가되는 멤버회원의 아이디)
	public void addFriendship(Long adderNum, String searchId) {

		Long addeeNum = memberRepository.findById(searchId).getNum();

		Friend friend = new Friend(adderNum, addeeNum);
		// DB 저장
		friendRepository.save(friend);
	}

	// 등록된 친구의 addeeNum을 가져오는 서비스
	public List<Friend> friendship(Long adderNum) {
		return friendRepository.findByAdderNum(adderNum);
	}

	// 모든 친구관계 조회하는 서비스
	public List<Friend> findAll() {
		return friendRepository.findAll();
	}

	// 친구 검색(조회)하는 서비스
	public Member searchMember(String id) {
		return memberRepository.findById(id);
	}

}
