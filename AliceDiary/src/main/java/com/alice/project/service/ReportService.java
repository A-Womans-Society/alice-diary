package com.alice.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Report;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.ReportRepository;
import com.alice.project.web.SearchDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 붙은 필드만 가진 생성자 만들어줌
@Slf4j
public class ReportService {

	private final ReportRepository reportRepository;
	private final MemberRepository memberRepository;

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

	public List<Report> checkExist(Long targetN, String userId) {
		Member member = memberRepository.findById(userId);
		return reportRepository.findPostReportExist(targetN, member.getNum());
	}

	/* 신고 목록 반환 */
	public Page<Report> findReports(Pageable pageable) {
		return reportRepository.findAll(pageable);
	}

	/* 신고 검색 기능 */
	public Page<Report> searchReport(SearchDto sdto, Pageable pageable) {
		Page<Report> reportList = null;

		String type = sdto.getType();
		String keyword = sdto.getKeyword();

		if (type.equals("reporterId")) { // 신고자 검색
			reportList = reportRepository.searchByReporterId(keyword, pageable);
		} else if (type.equals("content")) { // 신고내용 검색
			reportList = reportRepository.findByContentContaining(keyword, pageable);
		}
		return reportList;
	}

	public Page<Report> searchReportByTargetId(SearchDto sdto, Pageable pageable) {
		String type = sdto.getType(); // targetId (신고대상자 아이디)
		String keyword = sdto.getKeyword();

		Page<Report> reportList = reportRepository.findAll(pageable);
		List<Report> list = new ArrayList<>();
		for (Report report : reportList) {
			log.info("!!!!!!!!!!!!report.getReply().getNum() : " + report.getReply().getMember().getNum());
			if (report.getReportType().equals("POST")) {
				log.info("!!!!!!!!!!!!!!!!!!postid : " + report.getPost().getMember().getId());
				if (report.getPost().getMember().getId().contains(keyword)) {
					list.add(report);
				}
			} else if (report.getReportType().equals("REPLY")) {
				log.info("!!!!!!!!!!!!!!!!!!replyid : " + report.getReply().getMember().getId());
				if (report.getReply().getMember().getId().contains(keyword)) {
					list.add(report);
				}
			}
		}
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), list.size());
		reportList = new PageImpl<>(list.subList(start, end), pageable, list.size());

		log.info("!!!!!!!!!!!!!reportList.getTotalElements : " + reportList.getTotalElements());
		return reportList;
	}

	public Page<Report> searchReportByReportReason(SearchDto sdto, Pageable pageable) {
		// String type = sdto.getType();
		String keyword = sdto.getKeyword();

		Page<Report> reportList = reportRepository.findAll(pageable);
		List<Report> list = new ArrayList<>();
		for (Report report : reportList) {
			if (report.getReportReason().toString().equals(keyword)) {
				list.add(report);
			}
		}
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), list.size());
		reportList = new PageImpl<>(list.subList(start, end), pageable, list.size());

		return reportList;
	}

}
