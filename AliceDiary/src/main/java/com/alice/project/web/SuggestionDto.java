package com.alice.project.web;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionDto {
	
	private Long suggestNum;
	
	private String content;
	
	private LocalDateTime suggestDate;
	
	private String password;

	@Builder
	public SuggestionDto(Long suggestNum, String content, LocalDateTime suggestDate) {
		super();
		this.suggestNum = suggestNum;
		this.content = content;
		this.suggestDate = suggestDate;
	}

	public SuggestionDto() {
		super();
	}
	
	

}
