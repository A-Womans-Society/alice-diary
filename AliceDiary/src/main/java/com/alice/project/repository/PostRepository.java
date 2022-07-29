package com.alice.project.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;

@Repository
@Transactional(readOnly = true)
public interface PostRepository
		extends JpaRepository<Post, Long>{
	
	@Query(value = "select * from Post order by post_num desc", nativeQuery=true)
	Page<Post> findAll(Pageable pageable); // 전체 조회 및 페이징처리

	@Modifying
	@Transactional
	@Query("update Post p set p.viewCnt = p.viewCnt + 1 where p.num = :num")
	Integer viewCntUp(Long num);

	Post findByNum(Long num);

	@Modifying
	@Transactional
	@Query("update Post p set p.title = :title where p.num = :num")
	Integer editTitle(Long num, String title);

	@Modifying
	@Transactional
	@Query("update Post p set p.content = :content where p.num = :num")
	Integer editContent(Long num, String content);

	@Modifying
	@Transactional
	@Query("update Post p set p.updateDate = :updateDate where p.num = :num")
	Integer editDate(Long num, LocalDateTime updateDate);
	
	@Query(value = "select * from Post where title like '%'||:title||'%' order by post_num desc", nativeQuery = true)
	Page<Post> searchTitle(String title, Pageable pageable);

	@Query(value = "select * from Post where content like '%'||:content||'%' order by post_num desc", nativeQuery = true)
	Page<Post> searchContent(String content, Pageable pageable);
	
	@Query(value = "select * from Post where member_num = :memberNum order by post_num desc", nativeQuery=true)
	Page<Post> searchWriter(Long memberNum, Pageable pageable);

	
}