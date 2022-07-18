package com.alice.project.web;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchEventFormDto {
	private String startDateStr;
	private String endDateStr;
	private String content;
}
