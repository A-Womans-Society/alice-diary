package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.service.MemberService;
import com.alice.project.service.ReportService;
import com.alice.project.web.ReportDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
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

	@PostMapping("community/checkExist")
	@ResponseBody
	public int checkExist(Long targetNum, String userId) {
		return reportService.checkExist(targetNum, userId).size();
	}
	
	// 댓글신고하기
	@PostMapping("community/reportreply")
	@ResponseBody
	public boolean reportReply(@ModelAttribute ReportDto reportDto, String userId, String reportReason, String content) {
	
		Member member = memberService.findById(userId);
		reportService.replyReport(Report.createReplyReport(reportDto.getReplyNum(), reportReason, content, member));

		return true;
	}
}