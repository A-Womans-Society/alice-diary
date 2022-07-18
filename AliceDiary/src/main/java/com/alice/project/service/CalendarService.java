package com.alice.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Calendar;
import com.alice.project.domain.Member;
import com.alice.project.repository.CalendarRepository;
import com.alice.project.web.CalendarFormDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

	private final CalendarRepository calendarRepository;

	@Transactional
	public void addEvent(CalendarFormDto dto, Member m) {
		Calendar cal = Calendar.createCalendar(dto, m);
		calendarRepository.save(cal);
	}

	public List<Calendar> eventsList(Long num) {
		List<Calendar> events = calendarRepository.findByMemNum(num);
		return events;
	}

	public Calendar eventDetail(Long id) {
		Calendar event = calendarRepository.getById(id);
		return event;
	}

	@Transactional
	public void deleteEvent(Long id) {
		calendarRepository.deleteById(id);
	}

	public List<Calendar> alarm(Long num, LocalDate today) {
		return calendarRepository.getAlarmEvents(num, today);
	}

	public List<Calendar> searchByContent(Long num, String content) {
		return calendarRepository.findByContent(num, content);
	}

	public List<Calendar> searchByStart(Long num, String start) {
		return calendarRepository.findByStart(num, start);
	}

	public List<Calendar> searchByEnd(Long num, LocalDate end) {
		return calendarRepository.findByEnd(num, end);
	}

	public List<Calendar> searchByStartEnd(Long num, String start, LocalDate end) {
		return calendarRepository.findByStartEnd(num, start, end);
	}

	public List<Calendar> searchByContentEnd(Long num, String content, LocalDate end) {
		return calendarRepository.findByContentEnd(num, content, end);
	}

	public List<Calendar> searchByContentStart(Long num, String content, String start) {
		return calendarRepository.findByContentStart(num, content, start);
	}

	public List<Calendar> searchByAll(Long num, String content, String start, LocalDate end) {
		return calendarRepository.findByAll(num, content, start, end);
	}
}
