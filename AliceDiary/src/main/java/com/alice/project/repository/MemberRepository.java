package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, 
	QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

	// 회원번호로 찾기
	public Member findByNum(Long num);
	
	// 회원 아이디로 찾기
	public Member findById(String id);
	
}
