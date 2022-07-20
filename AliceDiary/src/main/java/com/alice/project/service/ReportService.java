package com.alice.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Report;
import com.alice.project.repository.ReportRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ReportService {
	
	@Autowired private ReportRepository reportRepository;

	// 게시글 신고하기
	@Transactional
	public Report postReport(Report report) {
		return reportRepository.save(report);
	}

}
