package com.alice.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.alice.project.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long>, QuerydslPredicateExecutor<Report>{

	/* 모든 신고목록 반환 */ 
	Page<Report> findAll(Pageable pageable);
	
	@Query("SELECT r FROM Report AS r WHERE mem_num LIKE '%'||:keyword||'%'")
	Page<Report> searchByReporterId(String keyword, Pageable pageable);

	Page<Report> findByContentContaining(String keyword, Pageable pageable);

	
}
