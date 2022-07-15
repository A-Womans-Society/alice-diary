package com.alice.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.ProfileRepository;
import com.alice.project.web.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	
	public Member findById(String id) {
		Member member = memberRepository.findById(id);
		return member;
	}
	
	public Optional<Member> findMemById(String id) {
		Optional<Member> updateMember = profileRepository.findById(id);
		return updateMember;
	}
	

}
