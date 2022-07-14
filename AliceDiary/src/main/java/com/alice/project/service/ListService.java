package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;

@Service
@Transactional
public class ListService {

	@Autowired
	private PostRepository postRepository;

	public Page<Post> list(Pageable pageable) {

		return postRepository.findAll(pageable);

	}

	

	/*
	 * public Page<Post> searchList(String keyword, Pageable pageable) {
	 * 
	 * return postRepository.findByTitleContaining(keyword, pageable); }
	 */

}
