package com.alice.project.domain;

import java.time.LocalDateTime;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Message implements Comparator<Message>, Comparable<Message>{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MESSAGE_SEQ_GENERATOR")
	@SequenceGenerator(name="MESSAGE_SEQ_GENERATOR", sequenceName="SEQ_MESSAGE_NUM", initialValue=1, allocationSize = 1)
	@Column(name="message_num")
	private Long msgNum; // 쪽지 번호
	private Long user1Num; // 쪽지 보내는회원 번호 -> 아래 객체있으니까 필요없지?
	private Long user2Num; // 쪽지 받는회원 번호
	private LocalDateTime sendDate; // 쪽지 발송일자
	private String content; // 쪽지내용
	private Long msgStatus; // 보낸사람 상태 (지우면 0, 안지우면 1)
	private Long direction; // user1Num->user2Num : 0, 반대면 1
	
	public Message(Long msgNum, Long user1Num, Long user2Num, LocalDateTime sendDate, String content, Long msgStatus, Long direction) {
		this.msgNum = msgNum;
		this.user1Num = user1Num;
		this.user2Num = user2Num;
		this.sendDate = sendDate;
		this.content = content;
		this.msgStatus = msgStatus;
		this.direction = direction;
	}
	
	@Builder
	public Message(Long user1Num, Long user2Num, LocalDateTime sendDate, String content, Long msgStatus, Long direction) {
		this.user1Num = user1Num;
		this.user2Num = user2Num;
		this.sendDate = sendDate;
		this.content = content;
		this.msgStatus = msgStatus;
		this.direction = direction;
	}
	
	
//	public Message(Long num, Long messageFromNum, Long messageToNum, 
//			LocalDateTime sendDate, String content,
//			Long senderStatus, Long receiverStatus) {
//		this.num = num;
//		this.messageFromNum = messageFromNum;
//		this.messageToNum = messageToNum;
//		this.sendDate = sendDate;
//		this.content = content;
//		this.senderStatus = senderStatus;
//		this.receiverStatus = receiverStatus;
//	}

	@Builder
//	public Message(Long messageFromNum, Long messageToNum, 
//			LocalDateTime sendDate, String content) {
//		this.messageFromNum = messageFromNum;
//		this.messageToNum = messageToNum;
//		this.sendDate = sendDate;
//		this.content = content;
//		this.senderStatus = 1L;
//		this.receiverStatus = 1L;
//	}

	@Override
	public int compare(Message o1, Message o2) {
		return o2.getSendDate().compareTo(o1.getSendDate());
	}

	@Override
	public int compareTo(Message m) {
		if (this.sendDate.isBefore(m.sendDate)) {
			return -1;
		} else if (this.sendDate.isAfter(m.sendDate)) {
			return 1;
		} else {
			return 0;
		}
	}



	
//	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
//	@JoinColumn(name="member_num")
//	private Member member; // 쪽지 보내는회원 객체
	
//	@OneToOne(mappedBy="message")
//	private AttachedFile file = new AttachedFile();
	
	// 연관관계 메서드 (양방향관계)
//	public void setMember(Member member) {
//		this.member = member;
//		member.getMessages().add(this);
//	}
//
//	// 쪽지 객체 생성 메서드
//	public static Message createMessage(Member member) {
//		Message message = new Message();
//		message.setMember(member);
//		return message;
//	}
}
