package com.alice.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;
import com.alice.project.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

	private final CommunityRepository comRepository;

	// 커뮤니티 생성하기
	@Transactional
	public Community create(Community com) {
		return comRepository.save(com);
	}
	
	//번호로 객체찾기
	public Community findByNum(Long num) {
		return comRepository.findByNum(num);
	}
	
	//번호로 커뮤니티이름찾기
	public String findNameByNum(Long num) {
		return comRepository.findNameByNum(num);
	}


}
