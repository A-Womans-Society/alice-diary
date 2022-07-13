package com.alice.project.service;

import java.util.List;

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
	
	// 친구추가 서비스(추가하는 사람, 추가되는 사람)
	public void addFriendship(Long adderNum, String searchId) {
		
		Long addeeNum = memberRepository.findById(searchId).getNum();
		
		Friend friend = new Friend(adderNum, addeeNum);
		// DB 저장
		friendRepository.save(friend);
	}

	// 모든 친구관계 조회하는 서비스 
//	public List<Friend> findAll() {
//		return friendRepository.findAll();
//		
//		// dto 를 활용하는 방법은?
//	}
//	
	// 친구 검색(조회)하는 서비스
	public Member searchMember(String id) {
		return memberRepository.findById(id);
	}
	
//	private void validateDuplicateMember(Member member) {
//		List<Member> findMember = mr.findOne(member.getId());
//		if (!findMember.isEmpty()) {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		}
//	}


}
