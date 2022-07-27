package com.alice.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	@Query("SELECT m FROM Friend AS m WHERE adder_num = :adderNum")
	List<Friend> findByAdderNum(Long adderNum);

	@Transactional
	@Query(value = "SELECT * FROM Friend WHERE adder_num = :adderNum AND addee_num = :addeeNum", nativeQuery = true)
	Friend findGroupByAddeeAdderNum(Long adderNum, Long addeeNum);

	@Transactional
	@Query(value = "SELECT * FROM Friend WHERE adder_num = :adderNum AND addee_num = :addeeNum", nativeQuery = true)
	List<Friend> checkAlreadyFriend(Long adderNum, Long addeeNum);

	@Query(value = "SELECT * FROM Friend WHERE addee_num = :num AND adder_num IN (SELECT addee_num\r\n"
			+ "FROM Friend WHERE adder_num = :num)", nativeQuery = true)
	List<Friend> weAreFriend(Long num);
}
