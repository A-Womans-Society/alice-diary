package com.alice.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;
import com.alice.project.domain.Status;

@Repository
public interface MemberRepository
		extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

	@Query(value = "select member_num from Member where id = :id", nativeQuery = true)
	Long findMemberNumById(String id);

	// 회원 아이디로 찾기
	Member findById(String id);

	// id중복 체크를 위한 메서드
	boolean existsById(String id);

	// id찾기
	Member findByNameAndMobileAndEmail(String name, String mobile, String email);

	// 비밀번호 찾기
	Member findByIdAndNameAndMobile(String id, String name, String mobile);

	// 회원번호로 찾기
	Member findByNum(Long num);

	@Query("SELECT m FROM Member AS m WHERE num IN (SELECT addeeNum FROM Friend WHERE adder_num = :adderNum) AND (name LIKE '%'||:friends||'%' OR id LIKE '%'||:friends||'%')")
	List<Member> findByIdOrName(Long adderNum, String friends);

	Member findByName(String name);

	@Query(value = "select * from Member where ID like '%'||:id||'%'", nativeQuery = true)
	Member findMemberById(String id);
	
	@Query("SELECT m FROM Member AS m WHERE id LIKE '%'||:keyword||'%'")
	Page<Member> searchById(String keyword, Pageable pageable);
	
	@Query("SELECT m FROM Member AS m WHERE num LIKE '%'||:keyword||'%'")
	Page<Member> searchByNum(Long keyword, Pageable pageable);
	
	Page<Member> findByNameContaining(String keyword, Pageable pageable);

	Page<Member> findByMobileContaining(String keyword, Pageable pageable);

	Page<Member> findByEmailContaining(String keyword, Pageable pageable);

	Page<Member> findByReportCnt(Long keyword, Pageable pageable);

	Page<Member> findByStatus(Status status, Pageable pageable);

}
