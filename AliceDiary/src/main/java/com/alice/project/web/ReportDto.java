package com.alice.project.web;

import java.time.LocalDateTime;

import com.alice.project.domain.ReportReason;
import com.alice.project.domain.ReportType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportDto {
	
	private String reporterId; // 신고자 아이디
	private Long targetNum; // 신고대상물 번호 (POST_NUM / REPLY_NUM)
	private String targetId; // 신고대상물 작성자 아이디 (게시물 작성자 / 댓글 작성자)
	private ReportReason reportReason; // 신고사유 [BAD, LEAK, SPAM, ETC]
	private String content; // 신고내용
	private LocalDateTime reportDate; // 신고일자
	private ReportType reportType; // 신고종류 [POST, REPLY]

}
