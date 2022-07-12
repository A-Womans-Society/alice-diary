package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;

@Service
public class ViewService {
	
	@Autowired
	private PostRepository postRepository;
	
	
}
