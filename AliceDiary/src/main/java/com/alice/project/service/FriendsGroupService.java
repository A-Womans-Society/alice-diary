package com.alice.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alice.project.domain.FriendsGroup;
import com.alice.project.domain.Member;
import com.alice.project.repository.FriendsGroupRepository;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendsGroupService {

	private final FriendsGroupRepository friendsGroupRepository;
	private final MemberRepository memberRepository;

	// FriendsGroupName
	public String getGroupName(Long groupNum) {
		FriendsGroup group = friendsGroupRepository.getById(groupNum);
		return group.getGroupName();
	}

	public FriendsGroup getGroupByNum(Long groupNum) {
		FriendsGroup group = friendsGroupRepository.findByNum(groupNum);
		return group;
	}
	
	// 그룹명 등록(그룹 생성 회원번호, 그룹이름)
	@Transactional
	public FriendsGroup addGroup(Long groupCreatorNum, String groupName) {
		//엔티티 조회
		Member groupCreator = memberRepository.findByNum(groupCreatorNum);
		FriendsGroup group = friendsGroupRepository.findByName(groupName);
		
		FriendsGroup friendsgroup = new FriendsGroup(groupName, groupCreatorNum);
		
		return friendsGroupRepository.save(friendsgroup);
	}
	
	// 그룹명 전체 조회
	public List<FriendsGroup> friendsGrouplist() {
		return friendsGroupRepository.findAll();
	}
}
