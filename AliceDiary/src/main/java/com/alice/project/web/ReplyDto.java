package com.alice.project.web;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReplyDto {

	private Long num;

	private Long ParentRepNum;

	private String content;

	private LocalDateTime repDate;

	private Boolean edit;

	private String memberId;

	private Long memberNum;

	private Long postNum;

	public ReplyDto(String content, String memberId, Long postNum) {
		this.content = content;
		this.memberId = "TESTER";
		this.postNum = postNum;
	}

	public ReplyDto(String content, LocalDateTime repDate, Boolean edit, String memberId, Long postNum) {
		super();
		this.content = content;
		this.repDate = repDate;
		this.edit = edit;
		this.memberId = "TESTER";
		this.postNum = postNum;
	}



}
