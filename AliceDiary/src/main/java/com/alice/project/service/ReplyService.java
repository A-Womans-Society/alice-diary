package com.alice.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Reply;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.repository.ReplyRepository;
import com.alice.project.web.ReplyDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostRepository postRepository;

	public Long getMemNumById(String memberId) {
		log.info("memberId : " + memberId);
		Long member = memberRepository.findMemberNumById(memberId);
		return member;
	}

	public Reply replyWrite(String memberId, Long postNum, String content) {
		log.info("memberId : " + memberId);

		Reply writedReply = new Reply(content, LocalDateTime.now(), Boolean.FALSE, postRepository.findByNum(postNum),
				memberRepository.findById(memberId));

		return replyRepository.save(writedReply);
	}

	public List<ReplyDto> replyList(Long num) {
		List<ReplyDto> result = new ArrayList<>();

		List<Reply> pList = replyRepository.findParentReplysByNum(num);
		for (Reply p : pList) {
			List<Reply> tmp = new ArrayList<Reply>();
			tmp.add(p);
			List<Reply> childL = replyRepository.findChildByParentNum(p.getNum());
			tmp.addAll(childL);
			for (Reply r : tmp) {
				ReplyDto rdto = new ReplyDto();
				rdto.setNum(r.getNum());
				rdto.setParentRepNum(r.getParentRepNum());
				rdto.setContent(r.getContent());
				rdto.setRepDate(r.getRepDate());
				rdto.setEdit(r.getEdit());
				rdto.setMemberId(r.getMember().getId());
				rdto.setPostNum(r.getPost().getNum());
				rdto.setHeart(r.getHeart());
				result.add(rdto);
			}

		}
		return result;
	}

	public Reply replyReplyWrite(String memberId, Long postNum, Long parentRepNum, String content) {
		log.info("parentRepNum : " + parentRepNum);

		Reply writedReplyReply = new Reply(content, LocalDateTime.now(), Boolean.FALSE, parentRepNum,
				postRepository.findByNum(postNum), memberRepository.findById(memberId));

		return replyRepository.save(writedReplyReply);
	}
}
