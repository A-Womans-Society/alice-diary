package com.alice.project.repository;
//Member엔티티를 데이터베이스에 저장하는 Interface

import org.springframework.data.jpa.repository.JpaRepository;
import com.alice.project.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findById(String id);

	boolean existsById(String id); // id중복 체크를 위한 메서드

	// 회원번호로 회원 객체 하나 가져오기
	Member findByNum(Long num);

}
