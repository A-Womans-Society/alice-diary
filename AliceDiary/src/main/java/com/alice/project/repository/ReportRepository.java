package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
	
	
}