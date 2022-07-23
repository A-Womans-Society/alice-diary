package com.alice.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private MemberRepository memberRepository;

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
	
	//게시글 신고유무판단
	public List<Report> postReportcheck(Long postNum, String userId){
		Member member = memberRepository.findById(userId);
		return reportRepository.findPostReportExist(postNum, member.getNum());
	}
	
	//댓글신고유무판단
		public List<Report> replyReportcheck(Long replyNum, String userId){
			Member member = memberRepository.findById(userId);
			return reportRepository.findReplyReportExist(replyNum, member.getNum());
		}
}
