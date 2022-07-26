package com.alice.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.repository.AttachedFileRepository;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.repository.ReplyRepository;
import com.alice.project.web.PostSearchDto;
import com.alice.project.web.WriteFormDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
public class PostService {

	@Autowired private PostRepository postRepository;

	@Autowired private MemberRepository memberRepository;
	
	@Autowired private AttachedFileRepository attachedFileRepository;	
	
	@Autowired private ReplyRepository replyRepository;

	// 글쓰기
	@Transactional
	public Post write(Post post) {
		return postRepository.save(post);
	}

	// 글번호로 post객체 하나 찾기
	public Post findOne(Long num) {
		return postRepository.findByNum(num);
	}

	// 게시글 전체 불러오기
	public Page<Post> list(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

	// 검색해서 리스트 불러오기
	public Page<Post> searchList(PostSearchDto postSearchDto, Pageable pageable) {

		log.info("서비스 로그 postSearchDto :" + postSearchDto.toString());
		Page<Post> searchList = null;
		if (postSearchDto.getType().equals("title")) {
			searchList = postRepository.searchTitle(postSearchDto.getKeyword(), pageable);
		} else if (postSearchDto.getType().equals("content")) {
			searchList = postRepository.searchContent(postSearchDto.getKeyword(), pageable);
		} else if (postSearchDto.getType().equals("writer")) {
			log.info("postSearchDto.getKeyword() :" + postSearchDto.getKeyword());
			Member member = memberRepository.findMemberById(postSearchDto.getKeyword());
			if (member != null) {
				searchList = postRepository.searchWriter(member.getNum(), pageable);
			} else {
				searchList = postRepository.searchWriter(0L, pageable);
			}
		}
		// 공개게시판 글들만 가져오기
		List<Post> temp = searchList.toList();
		for (Post p : searchList) {
			if (!p.getPostType().toString().equals("OPEN")) {
				temp.remove(p);
			}
		}
		final int start = (int)pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), temp.size());
		searchList = new PageImpl<>(temp.subList(start, end), pageable, temp.size());		
		return searchList;
	}
	
	//게시글 번호로 상세보기
	public Post postView(Long num) {
		return postRepository.findById(num).get();
	}

	//조회수 올리기
	@Transactional
	public int viewCntUp(Long num) {       
		return postRepository.viewCntUp(num);    
	}
	
	//글 수정하기
	@Transactional
	public void updatePost(Long num, WriteFormDto updateDto) {
		
		postRepository.editContent(num, updateDto.getContent());
		postRepository.editTitle(num, updateDto.getTitle());
		postRepository.editDate(num, LocalDateTime.now());
	}
	
	//게시글 삭제하기
	@Transactional
	public void deletePost(Long num) {
		log.info("포스트 지우러 옴!!!!!!!!!!!");
		Post deletePost = postRepository.findByNum(num);
		postRepository.delete(deletePost);
	}
	
	//게시글 지울때 첨부파일(전체)도 같이 지우기
	@Transactional
	public void deletePostwithFile(Long num) {
		log.info("파일 삭제 num:" + num);
		attachedFileRepository.deletePostwithFile(num);
	}
	
	//게시글 지울때 댓글도 같이 지우기
	@Transactional
	public void deletePostwithReply(Long num) {
		log.info("댓글 삭제");
		replyRepository.deletePostwithReply(num);
	}
	
	//게시글 수정에서 파일 하나 삭제하기
	@Transactional
	public Integer deleteOneFile(Long num) {
		Integer result = attachedFileRepository.deleteOneFile(num);
		return result;
	}
	
	/* 공지사항 관련 서비스 */
	/* 공지사항 전체 불러오기 */
	public Page<Post> notceList(Pageable pageable) {
		return postRepository.findAllNotices(pageable);
	}
	
	/* 공개게시판 게시물 전체 불러오기 */
	public Page<Post> openList(Pageable pageable) {
		return postRepository.findAllOpens(pageable);
	}
	
	/* 공지사항 검색 */
	public Page<Post> searchNoticeList(PostSearchDto postSearchDto, Pageable pageable) {

		log.info("서비스 로그 postSearchDto :" + postSearchDto.toString());
		Page<Post> searchList = null;
		if (postSearchDto.getType().equals("title")) {
			searchList = postRepository.searchTitle(postSearchDto.getKeyword(), pageable);
		} else if (postSearchDto.getType().equals("content")) {
			searchList = postRepository.searchContent(postSearchDto.getKeyword(), pageable);
		}
		List<Post> list = new ArrayList<>();
		if (!searchList.isEmpty()) {
			for (Post p : searchList) {
				if (p.getPostType().toString().equals("NOTICE")) {
					list.add(p);
				}
			}
			final int start = (int)pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), list.size());
			searchList = new PageImpl<>(list.subList(start, end), pageable, list.size());		
		}
		
		return searchList;
	}

}