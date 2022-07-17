package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	List<Reply> findByPostNum(Long num);
	
	@Modifying
	@Transactional
	@Query(value="delete from Reply where post_num = :num", nativeQuery=true)
	Integer deletePostwithReply(Long num);
	
}
