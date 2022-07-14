package com.alice.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;
import com.alice.project.web.UpdateFormDto;

@Service
public class UpdateService {

	@Autowired
	private PostRepository postRepository;

	public void updatePost(Long num, UpdateFormDto updateFormDto) {
		System.out.println("수정 서비스 작동, num: "+num);
		Post posttmp = postRepository.findByNum(num);
		
		System.out.println("updateFormDto.getTitle()"+updateFormDto.getTitle());
		
		posttmp.setTitle(updateFormDto.getTitle());
		posttmp.setContent(updateFormDto.getContent());
		posttmp.setUpdateDate(LocalDateTime.now());
		
		System.out.println("posttmp.getContent()"+posttmp.getContent());
		System.out.println("posttmp.getUpdateDate()"+posttmp.getUpdateDate());
		
		postRepository.save(posttmp);
	}
}
