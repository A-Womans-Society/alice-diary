package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, 
	QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
	
	// 회원 아이디로 찾기
	Member findById(String id); 

	//id중복 체크를 위한 메서드
	boolean existsById(String id); 
	
	//id찾기
	Member findByNameAndMobileAndEmail(String name, String mobile, String email);
	
	//비밀번호 찾기
	Member findByIdAndNameAndMobile(String id, String name, String mobile);
	
	// 회원번호로 찾기
	Member findByNum(Long num);
}
