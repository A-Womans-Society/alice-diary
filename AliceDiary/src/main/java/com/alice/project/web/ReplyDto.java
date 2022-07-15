package com.alice.project.web;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
	
	private Long num;	

	private Long ParentRepNum;
	
	private String content;
	
	private LocalDateTime repDate;
	
	private Boolean edit;
	
	private String memberId;
	
	private Long postNum;

	public ReplyDto(String content, String memberId, Long postNum) {
		super();
		this.content = content;
		this.memberId = memberId;
		this.postNum = postNum;
	}
	

	

}
