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
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_num =: num")
	FriendsGroup findByNum(Long num);
		
	// 그룹 이름
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_Name =: groupName")
	FriendsGroup findByName(String groupName);
	
	// 그룹생성 회원 번호
	@Query("SELECT m FROM FriendsGroup AS m WHERE group_creator_num =: groupCreatorNum")
	FriendsGroup findByGroupCreatorNum(Long groupCreatorNum);

	//@Query("SELECT m FROM Member AS m WHERE num IN (SELECT addeeNum FROM Friend WHERE adder_num = :adderNum) AND (name LIKE '%'||:friends||'%' OR id LIKE '%'||:friends||'%')")
	//FriendsGroup findByIdOrName(Long adderNum, String friends);
}
