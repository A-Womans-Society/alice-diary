package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Friend;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	@Query("SELECT m FROM Friend AS m WHERE adderNum = :adderNum")
	List<Friend> findByAdderNum(Long adderNum);

	Friend findByNum(Long addeeNum);
	
	// 이름 & 아이디 검색
	// Page<Friend> findAll(Pageable pageable); // 전체 조회 및 페이징 처리

	
}
