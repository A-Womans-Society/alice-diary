package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Message {
	
	@Id @GeneratedValue
	@Column(name="message_num")
	private Long num; // 쪽지 번호
//	private Long messageFromNum; // 쪽지 보내는회원 번호 -> 아래 객체있으니까 필요없지?
	private Long messageToNum; // 쪽지 받는회원 번호
	private LocalDateTime sendDate; // 쪽지 발송일자
	private String content; // 쪽지내용
	
	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name="member_num")
	private Member member; // 쪽지 보내는회원 객체
	
	@OneToOne(mappedBy="message")
	private File file = new File();
	
	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getMessages().add(this);
	}
	
	// 쪽지 객체 생성 메서드
	public static Message createMessage(Member member) {
		Message message = new Message();
		message.setMember(member);
		return message;
	}
}