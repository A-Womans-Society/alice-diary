package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	@Query(value = "SELECT r FROM Report r WHERE target_num = :targetN AND mem_num = :memberId AND report_type = 'POST'")
	List<Report> findPostReportExist(Long targetN, Long memberId);

}
