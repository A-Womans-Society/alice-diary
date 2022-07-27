package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.domain.Reply;
import com.alice.project.domain.Report;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.PostRepository;
import com.alice.project.repository.ReplyRepository;
import com.alice.project.repository.ReportRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;

	private final MemberRepository memberRepository;

	private final ReplyRepository replyRepository;

	private final PostRepository postRepository;

	// 게시글 신고하기
	@Transactional
	public Report postReport(Report report) {
		return reportRepository.save(report);
	}

	// 댓글 신고하기
	@Transactional
	public Report replyReport(Report report) {
		return reportRepository.save(report);
	}

	// 게시글 신고유무판단
	public List<Report> postReportcheck(Long postNum, String userId) {
		Member member = memberRepository.findById(userId);
		Post post = postRepository.findByNum(postNum);
		return reportRepository.findPostReportExist(post, member);
	}

	// 댓글신고유무판단
	public List<Report> replyReportcheck(Long replyNum, String userId) {
		Member member = memberRepository.findById(userId);
		Reply reply = replyRepository.findByNum(replyNum);

		return reportRepository.findReplyReportExist(reply, member);
	}

	public void deleteReportWithReply(Long replyNum) {
		reportRepository.deleteByReplyNum(replyNum);
	}

	public void deleteReportWithPost(Long postNum) {
		reportRepository.deleteByPostNum(postNum);
	}
}
