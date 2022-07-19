package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.PostRepository;
import com.alice.project.web.PostSearchDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ListService {

	@Autowired
	private PostRepository postRepository;


	public Page<Post> list(Pageable pageable) {

		return postRepository.findAll(pageable);

	}

	public Page<Post> searchList(PostSearchDto postSearchDto, Pageable pageable) {

		log.info("서비스 로그 postSearchDto :" + postSearchDto.toString());

		if (postSearchDto.getType().equals("title")) {
			Page<Post> searchList = postRepository.findByTitleContaining(postSearchDto.getKeyword(), pageable);
			return searchList;
		} else if (postSearchDto.getType().equals("content")) {
			Page<Post> searchList = postRepository.findByContentContaining(postSearchDto.getKeyword(), pageable);
			return searchList;
			
			  } else if (postSearchDto.getType().equals("writer")) { Page<Post> searchList
			  = postRepository.searchWriter(postSearchDto.getKeyword(), pageable); return
			  searchList;
			 

			/*
			 * List<Post> searchList = new ArrayList<>(); List<Member> memberList =
			 * memberRepository.findMemberNumByIdContaining(postSearchDto.getKeyword());
			 * List<Long> numList = new ArrayList<>(); for (Member m : memberList) {
			 * numList.add(m.getNum()); searchList = postRepository.findByMember(m.getNum(),
			 * pageable);
			 * 
			 * }
			 * 
			 * final int start = (int) pageable.getOffset(); final int end = Math.min((start
			 * + pageable.getPageSize()), searchList.size()); final Page<Post> pages = new
			 * PageImpl<>(searchList.subList(start, end), pageable, searchList.size());
			 * 
			 * return pages;
			 */

		} else {
			Page<Post> searchList = null;
			return searchList;
		}

	}

}
