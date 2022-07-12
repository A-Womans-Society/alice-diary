package com.alice.project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	Page<Post> findAll(Pageable pageable); //전체 조회 및 페이징처리
	
	Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    
}
