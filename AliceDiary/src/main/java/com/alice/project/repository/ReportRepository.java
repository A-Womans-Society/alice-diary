package com.alice.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long>, QuerydslPredicateExecutor<Report> {

	@Query(value = "SELECT r FROM Report r WHERE target_num = :targetN AND mem_num = :memberId AND report_type = 'POST'")
	List<Report> findPostReportExist(Long targetN, Long memberId);

	/* 모든 신고목록 반환 */
	Page<Report> findAll(Pageable pageable);
	
	/* 모든 신고목록 반환 */
	@Query(value = "select * from Report order by report_num desc", nativeQuery=true)
	List<Report> searchAll();
	
	@Query(value = "SELECT * FROM Report WHERE mem_num = :num", nativeQuery = true)
	Report searchByReporterId(Long num);

	Page<Report> findByContentContaining(String keyword, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Report WHERE reply_num = :replyNum", nativeQuery = true)
	void deleteByReplyNum(Long replyNum);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Report WHERE post_num = :postNum", nativeQuery = true)
	void deleteByPostNum(Long postNum);
	
}