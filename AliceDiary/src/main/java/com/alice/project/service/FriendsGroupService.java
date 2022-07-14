package com.alice.project.service;

import org.springframework.stereotype.Service;

import com.alice.project.domain.FriendsGroup;
import com.alice.project.repository.FriendsGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendsGroupService {

	private final FriendsGroupRepository friendsGroupRepository;
	
	public String getGroupName(Long groupNum) {
		FriendsGroup group = friendsGroupRepository.getById(groupNum);
		return group.getGroupName();
		
	}
}
