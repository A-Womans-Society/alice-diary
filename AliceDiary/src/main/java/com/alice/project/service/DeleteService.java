package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;

@Service
public class DeleteService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public void deletePost(Long num) {
		Post deletePost = postRepository.findByNum(num);
		postRepository.delete(deletePost);
	}
	

}
