package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alice.project.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	
}
