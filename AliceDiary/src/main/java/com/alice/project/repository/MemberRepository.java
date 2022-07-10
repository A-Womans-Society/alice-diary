package com.alice.project.repository;
//Member엔티티를 데이터베이스에 저장하는 Interface

import org.springframework.data.jpa.repository.JpaRepository;
import com.alice.project.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findById(String id); //회원가입 시 중복된 회원이 있는지 검사하기 위해 아이디로 회원을 검사할 수 있는 쿼리 메서드

}
