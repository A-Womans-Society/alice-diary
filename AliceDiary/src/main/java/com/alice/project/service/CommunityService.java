package com.alice.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.groovy.runtime.ArrayUtil;
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

	// 번호로 객체찾기
	public Community findByNum(Long num) {
		return comRepository.findByNum(num);
	}

	// 번호로 커뮤니티이름찾기
	public String findNameByNum(Long num) {
		return comRepository.findNameByNum(num);
	}

	// 번호로 소속회원 찾기
	public String findMemListByNum(Long num) {
		return comRepository.findMemListByNum(num);
	}

	// 커뮤니티 탈퇴하기
	public void resign(Long num, String memId) {
		String memberList = comRepository.findMemListByNum(num);
		StringBuffer memList = new StringBuffer();
		List<String> ls = new ArrayList<>(Arrays.asList(memberList.split(",")));
		ls.remove(memId);
		for(String i : ls) {
			memList.append(i).append(",");
		}
		memList.deleteCharAt(memList.lastIndexOf(","));
		System.out.println(memList.toString());
		String newMemList = memList.toString();
		
		comRepository.memberListUpdate(num, newMemList);
	}
}
