package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.service.MemberService;
import com.alice.project.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReportController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReportService reportService;

	// 게시글신고하기
	@PostMapping("community/reportpost")
	@ResponseBody
	public boolean reportPost(String userId, Long postNum, String reportReason, String content) {

		Member member = memberService.findById(userId);
		reportService.postReport(Report.createPostReport(postNum, reportReason, content, member));

		return true;
	}

	//게시글 신고유무 판단
	@PostMapping("community/postreportcheck")
	@ResponseBody
	public int postReportcheck(Long postNum, String userId) {
		return reportService.postReportcheck(postNum, userId).size();
	}
	
	// 댓글신고하기
	@PostMapping("community/reportreply")
	@ResponseBody
	public boolean reportReply(Long replyNum, String userId, String reportReason, String content) {
	
		Member member = memberService.findById(userId);
		reportService.replyReport(Report.createReplyReport(replyNum, reportReason, content, member));

		return true;
	}
	
	//댓글 신고유무 판단
	@PostMapping("community/replyreportcheck")
	@ResponseBody
	public int replyReportcheck(Long replyNum, String userId) {
		return reportService.replyReportcheck(replyNum, userId).size();
	}
}