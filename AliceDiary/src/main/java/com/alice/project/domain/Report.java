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
@Table(name="report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Report {
	
	@Id @GeneratedValue
	@Column(name="report_num")
	private Long num; // 신고 번호
	private Long target; // 신고대상 회원번호
	private ReportReason reportReason; // 신고사유 [BAD, LEAK, SPAM, ETC]
	private String content; // 신고내용
	private LocalDateTime reportDate; // 신고일자
	private ReportType reportType; // 신고종류 [POST, REPLY]
	
	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name="mem_num")
	private Member member; // 신고회원 객체
	
	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getReports().add(this);
	}
	
	// 신고 객체 생성 메서드
	public static Report createReport(Member member) {
		Report report = new Report();
		report.setMember(member);
		return report;
	}

}

