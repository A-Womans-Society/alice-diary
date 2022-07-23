package com.alice.project.web;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class MessageListDto { 
	private Long messageFromNum; // 보내는 사람 번호
	private Long messageToNum; // 받는사람 번호
	private LocalDateTime sendDate; // 발송일자
	private String recentContent; // 가장 최근 메시지
	private String messageFromId; // 보내는 사람 아이디
	private String messageToId; // 받는 사람 아이디

	@Builder
	public MessageListDto(Long messageFromNum, Long messageToNum, 
			LocalDateTime sendDate, String recentContent, String messageFromId, 
			String messageToId) {
		this.messageFromNum = messageFromNum;
		this.messageToNum = messageToNum;
		this.sendDate = sendDate;
		this.recentContent = recentContent;
		this.messageFromId = messageFromId;
		this.messageToId = messageToId;
	}
	
	public MessageListDto(Long messageFromNum, Long messageToNum, 
			String recentContent, String messageFromId, String messageToId) {
		this.messageFromNum = messageFromNum;
		this.messageToNum = messageToNum;
		this.sendDate = LocalDateTime.now();
		this.recentContent = recentContent;
		this.messageFromId = messageFromId;
		this.messageToId = messageToId;
	}


}
