package com.alice.project.web;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventAlarmDto {
	private String content;
	private String memberList;
	private LocalDate startDate;
}
