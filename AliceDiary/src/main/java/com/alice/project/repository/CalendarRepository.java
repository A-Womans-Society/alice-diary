package com.alice.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	@Transactional
	@Query(value = "SELECT * FROM Calendar c INNER JOIN Member m ON m.member_num = :num AND c.mem_num = m.member_num WHERE :today BETWEEN c.alarm AND c.start_date ORDER BY c.start_date", nativeQuery = true)
	List<Calendar> getAlarmEvents(Long num, LocalDate today);

	@Query("SELECT c " + "FROM Calendar AS c WHERE mem_num = :num ORDER BY start_date")
	List<Calendar> findByMemNum(Long num);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND content LIKE '%'||:content||'%' ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByContent(Long num, String content);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND start_date >= :start ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByStart(Long num, String start);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND end_date <= :end ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByEnd(Long num, LocalDate end);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND start_date >= :start AND end_date <= :end ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByStartEnd(Long num, String start, LocalDate end);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND end_date <= :end AND content LIKE '%'||:content||'%' ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByContentEnd(Long num, String content, LocalDate end);

	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND start_date >= :start AND content LIKE '%'||:content||'%' ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByContentStart(Long num, String content, String start);
	
	@Transactional
	@Query(value = "SELECT * FROM Calendar WHERE mem_num = :num AND start_date >= :start AND end_date <= :end AND content LIKE '%'||:content||'%' ORDER BY start_date", nativeQuery = true)
	List<Calendar> findByAll(Long num, String content, String start, LocalDate end);
}
