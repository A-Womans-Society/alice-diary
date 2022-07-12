package com.alice.project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.Calendar;
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
		List<Calendar> events = calendarService.eventsList();
		JSONObject obj = new JSONObject();
		JSONArray jArray = new JSONArray();

		if (events.size() != 0) {
			for (Calendar cal : events) {
				JSONObject jObj = new JSONObject();

				jObj.put("title", cal.getContent());
				jObj.put("start", "\"" + cal.getStartDate() + "\"");
				jObj.put("end", "\"" + cal.getEndDate() + "\"");
				jObj.put("backgroundColor", cal.getColor());
				jArray.add(jObj);
			}
		} else {
			JSONObject jObj = new JSONObject();

			jObj.put("title", "test");
			jObj.put("start", "2022-07-11");
			jObj.put("end", "2022-07-11");
			jObj.put("backgroundColor", "red");
			jArray.add(jObj);
		}
		obj.put("items", jArray);
		model.addAttribute("list", obj.toString());
		model.addAttribute("CalForm", new CalendarFormDto());
		return "alice/calendar";
	}

	@PostMapping("/addEvent")
	public String calendar(CalendarFormDto dto) {
		LocalDate startDate = LocalDate.parse(dto.getStartDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(dto.getEndDateStr(), DateTimeFormatter.ISO_DATE);
		dto.setStartDate(startDate);
		dto.setEndDate(endDate.plusDays(1));
		calendarService.addEvent(dto);
		return "redirect:/AliceDiary/alice";
	}
}
