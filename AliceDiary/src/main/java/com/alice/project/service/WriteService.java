package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;

@Service
// @Transactional
public class WriteService {

	@Autowired
	private PostRepository postRepository;
	
	public Post write(Post post) {
		
		return postRepository.save(post);

	}
	
	
}