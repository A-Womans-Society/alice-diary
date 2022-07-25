package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.FriendsGroup;
import com.alice.project.domain.Member;
@Repository
public interface FriendsGroupRepository extends JpaRepository<FriendsGroup, Long>  {
	// 그룹 넘버
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_num = :num")
	Long findByNum(Long num);
		
	// 그룹 이름
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_name = :groupName")
	String findByName(String groupName);
	
	// 그룹생성 회원 번호
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_creator_num = :groupCreatorNum")
	Long findByGroupCreatorNum(Long groupCreatorNum);

}
