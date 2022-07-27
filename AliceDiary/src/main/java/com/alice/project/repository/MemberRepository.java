package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;

@Repository
@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

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
	
	//멤버 신고수 올리기
	@Modifying
	@Transactional
	@Query(value = "update Member set report_cnt = report_cnt + 1 where member_num = :num", nativeQuery = true)
	Integer reportCntUp(Long num);
	
	//회원번호로 아이디 찾기
	@Query(value = "select id from Member where member_num = :num", nativeQuery = true)
	String findIdByNum(Long num);

	Member findAllById(String[] members);
}

