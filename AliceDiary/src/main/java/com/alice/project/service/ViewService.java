package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;

@Service
public class ViewService {

	@Autowired
	private PostRepository postRepository;


	public Post postView(Long num) {

		System.out.println("service run post");

		return postRepository.findById(num).get();
	}

	@Transactional
	public int viewCntUp(Long num) {       
		
		return postRepository.viewCntUp(num);    
	}

}
