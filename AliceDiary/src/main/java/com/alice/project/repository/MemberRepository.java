package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alice.project.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findById(String id); 

	//id중복 체크를 위한 메서드
	boolean existsById(String id); 
	
	//id찾기
	Member findByNameAndMobileAndEmail(String name, String mobile, String email);
	
	//비밀번호 찾기
	Member findByIdAndNameAndMobile(String id, String name, String mobile);
	
	Member findByNum(Long num);
}