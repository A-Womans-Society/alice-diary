package com.alice.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.project.repository.PostRepository;
import com.alice.project.web.WriteFormDto;

@Service
public class UpdateService {

	@Autowired
	private PostRepository postRepository;

	public void updatePost(Long num, WriteFormDto updateDto) {
		System.out.println("수정 서비스 작동, num: " + num);

		postRepository.editContent(num, updateDto.getContent());
		postRepository.editTitle(num, updateDto.getTitle());
		postRepository.editDate(num, LocalDateTime.now());
	}
	
}
