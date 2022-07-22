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

	/*
	 * @ModelAttribute("reportReasons") public ReportReason[] reportReasons() {
	 * return ReportReason.values(); }
	 */
	
	// 게시글신고하기
	@PostMapping("community/reportpost")
	@ResponseBody
	public boolean reportPost(String userId, Long postNum, String reportReason, String content) {
		
		log.info("!!!!!!!!!!!!!!!!!!!!userId  :" + userId);
		log.info("!!!!!!!!!!!!!!!!!!!!postNum  :" + postNum);
		log.info("!!!!!!!!!!!!!!!!!!!!repostReason  :" + reportReason);
		log.info("!!!!!!!!!!!!!!!!!!!!content  :" + content);
		
		Member member = memberService.findById(userId);
		reportService.postReport(Report.createPostReport(postNum,reportReason, content, member));

		return true;
	}

	// 댓글신고하기
	@PostMapping("community/reportreply")
	@ResponseBody
	public boolean reportReply(@ModelAttribute ReportDto reportDto, String userId, String reportReason, String content) {
		
		log.info("!!!!!!!!!!!!!!!!!!!!userId  :" + userId);
		log.info("!!!!!!!!!!!!!!!!!!!!replyNum  :" + reportDto.getReplyNum());
		log.info("!!!!!!!!!!!!!!!!!!!!repostReason  :" + reportReason);
		log.info("!!!!!!!!!!!!!!!!!!!!content  :" + content);
		
		Member member = memberService.findById(userId);
		reportService.replyReport(Report.createReplyReport(reportDto.getReplyNum(), reportReason, content, member));

		return true;
	}
}