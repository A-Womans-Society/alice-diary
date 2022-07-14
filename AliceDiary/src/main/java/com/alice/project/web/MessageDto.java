package com.alice.project.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.alice.project.domain.Message;
import com.alice.project.service.MessageService;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter
@NoArgsConstructor
@ToString
@Slf4j
public class MessageDto { 
	
	@Autowired private MessageService ms;

	private Long messageFromNum; // 보내는 사람 번호
	private Long messageToNum; // 받는사람 번호
	private LocalDateTime sendDate; // 발송일자
	private String content; // 가장 최근 메시지
	private String messageFromId; // 보내는 사람 아이디
	private String messageToId; // 받는 사람 아이디

	@Builder
	public MessageDto(Long messageFromNum, Long messageToNum, 
			LocalDateTime sendDate, String content,
			String messageFromId, String messageToId) {
		this.messageFromNum = messageFromNum;
		this.messageToNum = messageToNum;
		this.sendDate = sendDate;
		this.content = content;
		this.messageFromId = messageFromId;
		this.messageToId = messageToId;
	}
	
	public MessageDto(Long messageFromNum, Long messageToNum, 
			LocalDateTime sendDate, String content) {
		this.messageFromNum = messageFromNum;
		this.messageToNum = messageToNum;
		this.sendDate = sendDate;
		this.content = content;
		this.messageFromId = ms.findIdByNum(messageFromNum);
		this.messageToId = ms.findIdByNum(messageToNum);
	}	

	public MessageDto(Message message, MessageService ms) {
		this.messageFromNum = message.getMessageFromNum();
		this.messageToNum = message.getMessageToNum();
		this.sendDate = message.getSendDate();
		this.content = message.getContent();
		this.messageFromId = ms.findIdByNum(this.getMessageFromNum());
		this.messageToId = ms.findIdByNum(this.getMessageToNum());
	}


}
