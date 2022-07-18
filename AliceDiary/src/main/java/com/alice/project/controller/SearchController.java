package com.alice.project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.Calendar;
import com.alice.project.domain.Member;
import com.alice.project.service.CalendarService;
import com.alice.project.service.MemberService;
import com.alice.project.web.SearchEventFormDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alice")
public class SearchController {
	private final CalendarService calendarService;
	private final MemberService memberService;

	@GetMapping("/search")
	public String searchEvent(Model model) {
		model.addAttribute("dto", new SearchEventFormDto());
		model.addAttribute("resultEvents", null);
		return "alice/searchEvent";
	}

	@PostMapping("/search")
	public String searchEvent(Model model, SearchEventFormDto dto, @AuthenticationPrincipal UserDetails user) {
		log.info("event search post start");

		Member m = memberService.findById(user.getUsername());
		List<Calendar> resultEvents = new ArrayList<Calendar>();

		LocalDate endDate = (dto.getEndDateStr() != "")
				? LocalDate.parse(dto.getEndDateStr(), DateTimeFormatter.ISO_DATE).plusDays(1L)
				: null;
		// 모두 입력할 경우
		if (dto.getContent() != "" && dto.getStartDateStr() != "" && dto.getEndDateStr() != "") {
			log.info("search by all");
			resultEvents = calendarService.searchByAll(m.getNum(), dto.getContent(), dto.getStartDateStr(), endDate);

		} else if (dto.getContent() != "" && dto.getStartDateStr() != "") {
			// 내용, 시작 날짜만 입력할 경우
			log.info("search by content, start");
			resultEvents = calendarService.searchByContentStart(m.getNum(), dto.getContent(), dto.getStartDateStr());
		} else if (dto.getContent() != "" && dto.getEndDateStr() != "") {
			// 내용, 끝나는 날짜만 입력할 경우
			log.info("search by content, end");
			resultEvents = calendarService.searchByContentEnd(m.getNum(), dto.getContent(), endDate);

		} else if (dto.getStartDateStr() != "" && dto.getEndDateStr() != "") {
			// 시작 날짜, 끝나는 날짜로 검색
			log.info("search by start, end");
			resultEvents = calendarService.searchByStartEnd(m.getNum(), dto.getStartDateStr(), endDate);

		} else if (dto.getStartDateStr() != "") {
			// 시작하는 날짜로 검색
			log.info("search by start");
			resultEvents = calendarService.searchByStart(m.getNum(), dto.getStartDateStr());

		} else if (dto.getEndDateStr() != "") {
			// 끝나는 날짜로 검색
			log.info("search by end");
			resultEvents = calendarService.searchByEnd(m.getNum(), endDate);

		} else if (dto.getContent() != "") {
			// 내용으로 검색
			log.info("search by content");
			resultEvents = calendarService.searchByContent(m.getNum(), dto.getContent());
		}
		model.addAttribute("resultEvents", resultEvents);
		model.addAttribute("dto", dto);
		return "alice/searchEvent";
	}
}
