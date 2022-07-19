package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(of = "num")
@AllArgsConstructor
public class Reply {


	@Id
	@GeneratedValue
	@Column(name = "reply_num")
	private Long num; // 댓글 번호
	private Long parentRepNum; // 부모댓글 번호
	private String content; // 댓글 내용
	private LocalDateTime repDate; // 댓글작성일자
	private Long heart = 0L; // 공감 수 (default=0)
	private Boolean edit; // 수정여부 (False, True) SQL문 : CHAR(1) Check(edit IN('0', '1')
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_num")
	private Post post; // 댓글 소속 게시물 객체

	@ManyToOne(fetch = FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name = "member_num")
	private Member member; // 댓글 작성 회원 객체

	@PrePersist
	public void rep_Date() {
		this.repDate = LocalDateTime.now();
	}

	//댓글쓰기
	@Builder
	public Reply(String content, LocalDateTime repDate, Boolean edit, Post post, Member member) {
		super();

		this.content = content;
		this.repDate = repDate;
		this.edit = edit;
		this.post = post;
		this.member = member;
	}
	
	//대댓글쓰기
	@Builder
	public Reply(String content, LocalDateTime repDate, Boolean edit, Long parentRepNum, Post post, Member member) {
		super();

		this.content = content;
		this.repDate = repDate;
		this.edit = edit;
		this.parentRepNum = parentRepNum;
		this.post = post;
		this.member = member;
	}
	
	
	// 연관관계 메서드 (양방향관계)
	public void setPost(Post post) {
		this.post = post;
//		post.getReplies().add(this);
	}
  
	public void setMember(Member member) {
		this.member = member;
		member.getReplies().add(this);
	}
	
	/*
	 * // 댓글 객체 생성 메서드 public static Reply createReply(Post post, Member member) {
	 * Reply reply = new Reply(); reply.setPost(post); reply.setMember(member);
	 * return reply; }
	 */
}
