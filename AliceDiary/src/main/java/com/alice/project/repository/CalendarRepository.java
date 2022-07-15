package com.alice.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alice.project.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	@Query("SELECT c " + "FROM Calendar AS c " + "WHERE :today BETWEEN (c.alarm) AND c.startDate")
	List<Calendar> getAlarmEvents(LocalDate today);
}
