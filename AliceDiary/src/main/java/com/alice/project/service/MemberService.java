package com.alice.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{ //MemberService가 UserDetailService를 구현

	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
//		validateDuplicateMember(member);
		return memberRepository.save(member); //insert
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	// 개별 회원 조회
	public Member findByNum(Long num) {
		return memberRepository.getById(num);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}