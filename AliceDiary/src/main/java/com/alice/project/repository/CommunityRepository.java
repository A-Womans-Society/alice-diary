package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;
import com.alice.project.domain.Member;

@Repository
@Transactional(readOnly = true)
public interface CommunityRepository extends JpaRepository<Community, Long> {

	Community findByNum(Long num);

	@Query(value = "select name from community where community_num = :num", nativeQuery = true)
	String findNameByNum(Long num);

	@Query(value = "select member_list from community where community_num = :num", nativeQuery = true)
	String findMemListByNum(Long num);

	@Modifying
	@Transactional
	@Query("update Community c set c.memberList = :memList where c.num = :num")
	void memberListUpdate(Long num, String memList);

	@Query(value = "select member_num from community where community_num = :num", nativeQuery = true)
	Long findMemberNumByNum(Long num);

	@Modifying
	@Transactional
	@Query("update Community c set c.name = :name where c.num = :num")
	Integer nameEdit(Long num, String name);

	@Modifying
	@Transactional
	@Query("update Community c set c.description = :description where c.num = :num")
	Integer descriptionEdit(Long num, String description);

	@Modifying
	@Transactional
	@Query("delete from Community c where c.num = :comNum")
	void deleteCom(Long comNum);

	// 내가 만든 커뮤니티 목록 가져오기
	List<Community> findByMember(Member member);

	@Query(value = "select * from community", nativeQuery = true)
	List<Community> getAll();

}