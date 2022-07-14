package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Friend;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

	List<Friend> findByAdderNum(Long adderNum);
	
	
	//	Page<Friend> findAll(Pageable pageable); // 전체 조회 및 페이징 처리
	// 사용자입장에서 내 친구목록
	// SELECT * FROM friend WHERE adder=?
	//List<Friend> findAddFriendForm(String addeeNum);
	// 아이디로 회원 조회
	
	
}
