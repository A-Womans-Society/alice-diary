package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.alice.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, 
	QuerydslPredicateExecutor<Post>, PostRepositoryCustom {

	public List<Post> findByMemberNum(Long memberNum);	
	
	
}
