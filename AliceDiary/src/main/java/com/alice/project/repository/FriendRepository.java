package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	@Query("SELECT m FROM Friend AS m WHERE adder_num = :adderNum")
	List<Friend> findByAdderNum(Long adderNum);

//	Friend findByNum(Long addeeNum);

	@Query("SELECT m FROM Friend AS m WHERE adder_num = :adderNum AND addeeNum = :addeeNum")
	Friend findGroupByAddeeAdderNum(Long adderNum, Long addeeNum);

}
