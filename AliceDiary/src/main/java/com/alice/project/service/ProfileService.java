package com.alice.project.service;

import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;
import com.alice.project.web.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final MemberRepository memberRepository;
	
	public Member findById(String id) {
		Member member = memberRepository.findById(id);
		return member;
	}
	

}
