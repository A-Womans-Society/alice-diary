package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	//Member findBynum(Long num);

	@Query(value="select member_num from Member where id = :id", nativeQuery=true)
	Long findMemberNumById(String id);

	Member findById(String memberId);
}
