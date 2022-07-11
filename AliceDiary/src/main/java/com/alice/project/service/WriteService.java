package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.web.WriteFormDto;

@Service
@Transactional
public class WriteService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	public Post write(WriteFormDto writeFormDto, Long memberNum) {
		Member member = memberRepository.findByMemberNum(memberNum);
		Post post = Post.createPost(writeFormDto.getTitle(), writeFormDto.getContent(), 
				member);
		
		System.out.println("post.getTitle() :" + post.getTitle());
		System.out.println("post.getContent() :" + post.getContent());

		return postRepository.save(post);

	}
	
	
}