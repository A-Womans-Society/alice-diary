package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	 Member findBynum(Long num);

	Member findById(String memberId);
}
