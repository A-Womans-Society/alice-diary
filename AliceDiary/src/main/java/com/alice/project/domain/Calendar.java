package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "calendar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Calendar {

	@Id
	@GeneratedValue
	@Column(name = "calendar_num")
	private Long num; // 일정 번호
	private String memberList; // 참여자 리스트
	private LocalDateTime startDate; // 일정 시작일자
	private LocalDateTime endDate; // 일정 종료일자
	private String content; // 일정내용
	private String memo; // 일정메모
	private String location; // 일정 장소
	private Boolean color; // 일정 색
	private Boolean publicity; // 일정 공개여부
	private String alarm; // 일정 알람

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mem_num")
	private Member member; // 일정 생성 회원 객체

	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getCalendars().add(this);
	}

	// 일정 객체 생성 메서드
	public static Calendar createCalendar(Member member) {
		Calendar calendar = new Calendar();
		calendar.setMember(member);
		return calendar;
	}

}