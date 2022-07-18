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

	@Query("SELECT c " + "FROM Calendar AS c " + "WHERE mem_num = :num")
	List<Calendar> findByMemNum(Long num);
}
