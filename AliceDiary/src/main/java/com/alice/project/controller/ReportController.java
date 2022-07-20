package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.domain.ReportReason;
import com.alice.project.service.MemberService;
import com.alice.project.service.ReportService;
import com.alice.project.web.ReportDto;
import com.alice.project.web.WriteFormDto;

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
	public String reportPost(ReportDto reportDto, @AuthenticationPrincipal UserDetails user) {
		log.info("controller 실행");
		log.info("repostDto.getPostNum() :" + reportDto.getPostNum());

		Member member = memberService.findById(user.getUsername());
		Report report = Report.createPostReport(reportDto, member);

		log.info("report 객체생성 완료:" + report);

		reportService.postReport(report);
		String num = String.valueOf(reportDto.getPostNum());

		return "redirect:/community/get?num=" + num;
	}

}