package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.domain.ReportReason;
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

	@ModelAttribute("reportReasons")
	public ReportReason[] reportReasons() {
		return ReportReason.values();
	}

	// 신고하기
	@GetMapping("community/reportpost")
	public String reportPost(Model model) {
		log.info("신고하기 GET");
		model.addAttribute("reportDto", new ReportDto());

		return "community/postView";
	}

	// 신고하기
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

}