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
public class MsgFileDto {
	
	private String theOtherId;
	private String originName;
	private Long fileNum;
	private LocalDateTime sendDate;
	
	public MsgFileDto(String theOtherId, String originName, Long fileNum, LocalDateTime sendDate) {
		this.theOtherId = theOtherId;
		this.originName = originName;
		this.fileNum = fileNum;
		this.sendDate = sendDate;
	}
	
}
