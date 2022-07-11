package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Calendar;
import com.alice.project.repository.CalendarRepository;
import com.alice.project.web.CalendarFormDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

	private final CalendarRepository calendarRepository;

	@Transactional
	public void addEvent(CalendarFormDto dto) {
		Calendar cal = Calendar.createCalendar(dto);
		calendarRepository.save(cal);
	}

	public List<Calendar> eventsList() {
		List<Calendar> events = calendarRepository.findAll();
		return events;
	}
}
