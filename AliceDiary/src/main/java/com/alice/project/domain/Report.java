package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alice.project.web.ReportDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT_SEQ_GENERATOR")
	@SequenceGenerator(name = "REPORT_SEQ_GENERATOR", sequenceName = "SEQ_REPORT_NUM", initialValue = 1, allocationSize = 1)
	@Column(name = "report_num")
	private Long num; // 신고 번호

	private Long targetNum; // 신고대상물 번호

	@Enumerated(EnumType.STRING)
	private ReportReason reportReason; // 신고사유 [BAD, LEAK, SPAM, ETC]

	private String content; // 신고내용
	private LocalDateTime reportDate; // 신고일자

	@Enumerated(EnumType.STRING)
	private ReportType reportType; // 신고종류 [POST, REPLY]

	@ManyToOne(fetch = FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name = "mem_num")
	private Member member; // 신고회원 객체

	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getReports().add(this);
	}

	@PrePersist
	public void reportDate() {
		this.reportDate = LocalDateTime.now();
	}

	// 게시글 신고 객체 생성 메서드
	public static Report createPostReport(Long postNum, String reportReason, String content, Member member) {
		Report report = new Report(postNum, ReportReason.valueOf(reportReason), content, LocalDateTime.now(),
				ReportType.POST, member);

		return report;
	}

	@Builder
	public Report(Long targetNum, ReportReason reportReason, String content, LocalDateTime reportDate,
			ReportType reportType, Member member) {
		super();
		this.targetNum = targetNum;
		this.reportReason = reportReason;
		this.content = content;
		this.reportDate = reportDate;
		this.reportType = reportType;
		this.member = member;
	}

	// 게시글 신고 객체 생성 메서드
	public static Report createReplyReport(Long replyNum, String reportReason, String content, Member member) {
		Report report = new Report(replyNum, ReportReason.valueOf(reportReason), content, LocalDateTime.now(),
				ReportType.REPLY, member);

		return report;
	}

	
}
