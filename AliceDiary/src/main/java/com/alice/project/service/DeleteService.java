package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Post;
import com.alice.project.repository.AttachedFileRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.repository.ReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeleteService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private AttachedFileRepository attachedFileRepository;
	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void deletePost(Long num) {
		log.info("포스트 지우러 옴!!!!!!!!!!!");
		Post deletePost = postRepository.findByNum(num);
		postRepository.delete(deletePost);
	}

	

	@Transactional
	public void deletePostwithFile(Long num) {
		log.info("파일 삭제 num:" + num);

		attachedFileRepository.deletePostwithFile(num);

	}
	
	@Transactional
	public void deletePostwithReply(Long num) {
		log.info("댓글 삭제");

		replyRepository.deletePostwithReply(num);

	}
	
	
	
	@Transactional
	public Integer deleteOneFile(Long num) {
		Integer result = attachedFileRepository.deleteOneFile(num);
		return result;

	}
}
