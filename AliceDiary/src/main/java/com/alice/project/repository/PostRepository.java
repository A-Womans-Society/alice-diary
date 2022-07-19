package com.alice.project.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;

@Repository
@Transactional(readOnly = true)
public interface PostRepository
		extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {

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

	Page<Post> findByTitleContaining(String title, Pageable pageable);

	Page<Post> findByContentContaining(String content, Pageable pageable);

	
	@Query(value = "select p from Post as p where p.memberNum in (select m.memberNum from Member as m where m.id like '%'||:writer||'%')", nativeQuery=true)
	Page<Post> searchWriter(String writer, Pageable pageable);

}
