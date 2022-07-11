package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 붙은 필드만 가진 생성자 만들어줌
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	/* 회원 전체 조회 */
	// 값을 가져오는 메서드에서는 기본 읽기전용옵션 적용됨
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/* 개별 회원 조회 */
	// 값을 가져오는 메서드에서는 기본 읽기전용옵션 적용됨
	public Member findOne(Long memberNum) {
		Member member = memberRepository.findByNum(memberNum);
		return member;
	}
	
	/* 개별 회원 삭제 */
	public void deleteOne(Long memberNum) {
		Member member = memberRepository.findByNum(memberNum);
		memberRepository.save(Member.changeMemberOut(member));
	}
}
