package com.alice.project.repository;
import java.util.List;
//Member엔티티를 데이터베이스에 저장하는 Interface
import org.springframework.data.jpa.repository.JpaRepository;
import com.alice.project.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findById(String id); // id 검색

	boolean existsById(String id); // id중복 체크를 위한 메서드
	
	Member findByNum(Long addeeNum); // 회원번호로 회원 객체 하나 가져오기
	
	// List<Member> findAll(Long num);
	// List<Member> findByNum(Friend memberNum);
}
