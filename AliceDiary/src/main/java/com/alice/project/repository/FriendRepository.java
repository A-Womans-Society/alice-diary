package com.alice.project.repository;

import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Friend;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

	
//	Page<Friend> findAll(Pageable pageable); // 전체 조회 및 페이징 처리
	// 사용자입장에서 내 친구목록
	// SELECT * FROM friend WHERE adder=?;
	
	// 친구목록 페이징 처리
	//List<Friend> findAddFriendForm(String addeeNum); // 아이디로 회원 조회
	
	
}
