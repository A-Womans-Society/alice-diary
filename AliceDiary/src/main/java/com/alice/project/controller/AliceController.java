package com.alice.project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.repository.CalendarRepository;
import com.alice.project.service.CalendarService;
import com.alice.project.web.CalendarFormDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/AliceDiary")
@RequiredArgsConstructor
public class AliceController {
	private final CalendarService calendarService;

	@GetMapping("/alice")
	public String calendar(Model model) {
		
		model.addAttribute("list", "asdf");
		model.addAttribute("CalForm", new CalendarFormDto());
		return "alice/calendar";
	}

	@PostMapping("/addEvent")
	public String calendar(CalendarFormDto dto) {
		LocalDate startDate = LocalDate.parse(dto.getStartDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(dto.getEndDateStr(), DateTimeFormatter.ISO_DATE);
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		calendarService.addEvent(dto);
		return "redirect:/AliceDiary/alice";
	}
}
