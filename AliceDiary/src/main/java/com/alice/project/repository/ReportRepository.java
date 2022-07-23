package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	// 게시글 신고 유무 판단
	@Query(value = "SELECT r FROM Report r WHERE target_num = :postNum AND mem_num = :memberId AND report_type = 'POST'")
	List<Report> findPostReportExist(Long postNum, Long memberId);

	// 댓글글 신고 유무 판단
	@Query(value = "SELECT r FROM Report r WHERE target_num = :replyNum AND mem_num = :memberId AND report_type = 'REPLY'")
	List<Report> findReplyReportExist(Long replyNum, Long memberId);
}
