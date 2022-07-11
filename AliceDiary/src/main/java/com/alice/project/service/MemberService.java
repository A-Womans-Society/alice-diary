package com.alice.project.service;


import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional //로직을 처리하다가 에러 발생 시 변경된 데이터를 로직 수행 이전 상태로 콜백
@RequiredArgsConstructor //final 필드 생성자 생성해줌
public class MemberService {

	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
//		validateDuplicateMember(member);
		return memberRepository.save(member); //insert
	}
	
	public int checkIdDuplicate(String id) {
		boolean check = memberRepository.existsById(id);
		if(check) {
			return 1; //아이디 중복이면 1
		}else {
			return 0; //사용 가능 아이디면 0
		}
	}
	
 }
