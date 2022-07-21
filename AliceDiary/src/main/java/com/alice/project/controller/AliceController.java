package com.alice.project.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Calendar;
import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.service.CalendarService;
import com.alice.project.service.FriendService;
import com.alice.project.service.MemberService;
import com.alice.project.web.CalendarFormDto;
import com.alice.project.web.EventAlarmDto;
import com.alice.project.web.FriendshipDto;
import com.alice.project.web.SearchEventFormDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AliceController {
	private final CalendarService calendarService;
	private final MemberService memberService;
	private final FriendService friendService;

	@GetMapping("/alice")
	public String calendar(Model model, @AuthenticationPrincipal UserDetails user) {
		log.info("user : " + user.getUsername());
		Member member = memberService.findById(user.getUsername());
		// events list
		List<Calendar> events = calendarService.eventsList(member.getNum());
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
		// alarm list
		LocalDate today = LocalDate.now();
		List<EventAlarmDto> alarmList = calendarService.alarm(member.getNum(), today);
		// friends list
		List<Friend> friendsList = friendService.friendship(member.getNum());
		List<FriendshipDto> friendsDtoList = new ArrayList<FriendshipDto>();
		for (Friend f : friendsList) {
			FriendshipDto tmp = new FriendshipDto();
			Member m = memberService.findByNum(f.getAddeeNum());
			tmp.setNum(m.getNum());
			tmp.setName(m.getName());
			friendsDtoList.add(tmp);
		}
		model.addAttribute("dto", new SearchEventFormDto());
		model.addAttribute("alarmList", alarmList);
		model.addAttribute("list", obj.toString());
		model.addAttribute("friendsList", friendsDtoList);
		CalendarFormDto dto = new CalendarFormDto();
		dto.setPublicity(true);
		dto.setColor("crimson");
		model.addAttribute("CalForm", dto);
		model.addAttribute("today", today);
		return "alice/calendar";
	}

	@PostMapping("/addEvent")
	public String calendar(CalendarFormDto dto, @AuthenticationPrincipal UserDetails user) {
		LocalDate startDate = LocalDate.parse(dto.getStartDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(dto.getEndDateStr(), DateTimeFormatter.ISO_DATE);
		LocalDate alarmDate = startDate.minusDays(Long.parseLong(dto.getAlarm()));
		dto.setStartDate(startDate);
		dto.setEndDate(endDate.plusDays(1));
		dto.setAlarmDate(alarmDate);
		Member member = memberService.findById(user.getUsername());
		calendarService.addEvent(dto, member);
		return "redirect:/alice";
	}

	@PostMapping("/showDetail")
	@ResponseBody
	public JSONObject showDetail(@RequestParam(value = "id") String id, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		Calendar event = calendarService.eventDetail(Long.parseLong(id));
		JSONObject jObj = new JSONObject();

		if (event.getMemberList() != null) {
			String fNames = "";
			for (String n : event.getMemberList().split(",")) {
				fNames += memberService.findByNum(Long.parseLong(n)).getName() + " ";
			}
			jObj.put("memberList", fNames);
		} else {
			jObj.put("memberList", null);
		}

		jObj.put("title", event.getContent());
		jObj.put("id", event.getNum());
		jObj.put("start", event.getStartDate());
		jObj.put("end", event.getEndDate().minusDays(1L));
		jObj.put("backgroundColor", event.getColor());
		jObj.put("location", event.getLocation());
		jObj.put("memo", event.getMemo());
		jObj.put("publicity", event.getPublicity());
		jObj.put("alarm", Period.between(event.getAlarm(), event.getStartDate()).getDays() + "일 전");

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
