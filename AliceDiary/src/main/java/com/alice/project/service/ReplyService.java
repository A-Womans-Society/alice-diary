package com.alice.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Reply;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.repository.ReplyRepository;

@Service
public class ReplyService {
	
	@Autowired
    private ReplyRepository replyRepository;
	
	@Autowired
    private PostRepository postRepository;
	
	@Autowired
    private MemberRepository memberRepository;
	

    public Reply replyWrite(String memberId, Long postNum, String content){
    	Reply writedReply = new Reply(
    			content,
    			LocalDateTime.now(),
    			Boolean.FALSE,
    			postRepository.findByNum(postNum),
    			memberRepository.findById(memberId));

        return replyRepository.save(writedReply);
    }
	
}
