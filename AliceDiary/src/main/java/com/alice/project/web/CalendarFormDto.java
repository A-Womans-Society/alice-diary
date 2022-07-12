package com.alice.project.web;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarFormDto {
	private Long num; // 일정 번호
	private String memberList; // 참여자 리스트
	private String startDateStr; // 일정 시작일자
	private String endDateStr; // 일정 종료일자
	private LocalDate startDate;
	private LocalDate endDate;
	private String content; // 일정내용
	private String memo; // 일정메모
	private String location; // 일정 장소
	private String color; // 일정 색
	private Boolean publicity; // 일정 공개여부
	private String alarm; // 일정 알람
}
