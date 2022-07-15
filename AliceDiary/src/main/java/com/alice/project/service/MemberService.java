package com.alice.project.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Status;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 붙은 필드만 가진 생성자 만들어줌
@Slf4j
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final EntityManager em;
	
	/* 회원 전체 조회 */
	// 값을 가져오는 메서드에서는 기본 읽기전용옵션 적용됨
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/* 개별 회원 조회 */
	// 값을 가져오는 메서드에서는 기본 읽기전용옵션 적용됨
	public Member findOne(Long memberNum) {
		Member member = memberRepository.findByNum(memberNum);
		
		return member == null ? null : member;
	}
	
	/* 개별 회원 삭제 */
	public int deleteOne(Long memberNum) {
		log.info("여기까지는 오나??????????????");
		Member member = memberRepository.findByNum(memberNum);
		log.info("!!!!!!!!!!deleteOne 실행하고 원래:" + member.getNum());
		Member resultMember = memberRepository.save(Member.changeMemberOut(member));
		em.flush();
		if (!resultMember.getStatus().equals(Status.USER_OUT)) {
			return 0; // 탈퇴회원 처리가 안 됐으면 0 반환
		}
		log.info("deleteOne 실행하고 나서 resultMember.getStatus():" + resultMember.getStatus());
		return 1; // 탈퇴회원 처리가 됐으면 1 반환
	}
	
	/* 개별 회원 복구 */
	public int returnOne(Long memberNum) {
		log.info("여기까지는 오나??????????????");
		Member member = memberRepository.findByNum(memberNum);
		log.info("!!!!!!!!!!returnOne 실행하고 원래:" + member.getNum());
		Member resultMember = memberRepository.save(Member.changeMemberIn(member));
		em.flush();
		if (!resultMember.getStatus().equals(Status.USER_IN)) {
			return 0; // 탈퇴회원 처리가 안 됐으면 0 반환
		}
		log.info("returnOne 실행하고 나서 resultMember.getStatus():" + resultMember.getStatus());
		return 1; // 탈퇴회원 처리가 됐으면 1 반환
	}
}
