package com.alice.project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Calendar;
import com.alice.project.service.CalendarService;
import com.alice.project.web.CalendarFormDto;

import lombok.RequiredArgsConstructor;

@Controller
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
				jObj.put("id", cal.getNum());
				jObj.put("start", "\"" + cal.getStartDate() + "\"");
				jObj.put("end", "\"" + cal.getEndDate() + "\"");
				jObj.put("backgroundColor", cal.getColor());
				jObj.put("borderColor", cal.getColor());

				jArray.add(jObj);
			}
		} else {
			JSONObject jObj = new JSONObject();
			jArray.add(jObj);
		}
		obj.put("items", jArray);
		LocalDate today = LocalDate.now();
		List<Calendar> alarmList = calendarService.alarm(today);
		model.addAttribute("alarmList", alarmList);
		model.addAttribute("list", obj.toString());
		model.addAttribute("CalForm", new CalendarFormDto());
		model.addAttribute("today", today);
		return "alice/calendar";
	}

	@PostMapping("/addEvent")
	public String calendar(CalendarFormDto dto) {
		LocalDate startDate = LocalDate.parse(dto.getStartDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(dto.getEndDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate alarmDate = startDate.minusDays(Long.parseLong(dto.getAlarm()));
		dto.setStartDate(startDate);
		dto.setEndDate(endDate.plusDays(1));
		dto.setAlarmDate(alarmDate);
		calendarService.addEvent(dto);
		return "redirect:/AliceDiary/alice";
	}

	@PostMapping("/showDetail")
	@ResponseBody
	public JSONObject showDetail(@RequestParam(value = "id") String id, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		Calendar event = calendarService.eventDetail(Long.parseLong(id));
		JSONObject jObj = new JSONObject();

		jObj.put("title", event.getContent());
		jObj.put("id", event.getNum());
		jObj.put("start", event.getStartDate());
		jObj.put("end", event.getEndDate());
		jObj.put("backgroundColor", event.getColor());
		jObj.put("memberList", event.getMemberList());
		jObj.put("location", event.getLocation());
		jObj.put("memo", event.getMemo());
		jObj.put("publicity", event.getPublicity());
		jObj.put("alarm", event.getAlarm());

		return jObj;
	}

	@PostMapping("/deleteEvent")
	@ResponseBody
	public boolean deleteEvent(@RequestParam(value = "id") String id, HttpServletRequest req,
			HttpServletResponse resp) {
		calendarService.deleteEvent(Long.parseLong(id));

		return true;
	}
}