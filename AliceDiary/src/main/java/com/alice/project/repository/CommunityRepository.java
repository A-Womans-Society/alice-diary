package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;

@Repository
@Transactional(readOnly = true)
public interface CommunityRepository extends JpaRepository<Community, Long>{
	
	Community findByNum(Long num);
	
	@Query(value="select name from community where community_num = :num", nativeQuery=true)
	String findNameByNum(Long num);
	
	@Query(value="select member_list from community where community_num = :num", nativeQuery=true)
	String findMemListByNum(Long num);
	
	@Modifying
	@Transactional
	@Query("update Community c set c.memberList = :memList where c.num = :num")
	void memberListUpdate(Long num, String memList);

}
