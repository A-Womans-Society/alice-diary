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

import com.alice.project.service.ReplyService;
import com.alice.project.web.ReplyDto;

import lombok.AccessLevel;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Reply {


	@Id
	@GeneratedValue
	@Column(name = "reply_num")
	private Long num; // 댓글 번호
	private Long parentRepNum; // 부모댓글 번호
//	private Long memberNum; // 댓글 작성자
//	private Long postNum;
//	private String repWriter; // 댓글 작성자

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

	/*
	 * public Reply(ReplyDto rdto, ReplyService rs) { this.content =
	 * rdto.getContent(); this.repDate = rdto.getRepDate(); this.edit =
	 * rdto.getEdit(); this.memberNum = rs.getMemNumById(rdto.getMemberId());
	 * this.postNum = rdto.getPostNum(); }
	 * 
	 * @Builder public Reply(Long parentRepNum, Long memberNum, Long postNum, String
	 * content, LocalDateTime repDate, Boolean edit) { this.parentRepNum =
	 * parentRepNum; this.memberNum = memberNum; this.postNum = postNum;
	 * this.content = content; this.repDate = repDate; this.edit = edit; }
	 */

	@Builder
	public Reply(String content, LocalDateTime repDate, Boolean edit, Post post, Member member) {
		super();

		this.content = content;
		this.repDate = repDate;
		this.edit = edit;
		this.post = post;
		this.member = member;
	}
	
	@Builder
	public Reply(String content2, LocalDateTime now, Boolean false1, Long postNum, Long findMemberNumById) {
		// TODO Auto-generated constructor stub
	}

}
	
	// 연관관계 메서드 (양방향관계)
	public void setPost(Post post) {
		this.post = post;
		post.getReplies().add(this);
	}
	public void setMember(Member member) {
		this.member = member;
		member.getReplies().add(this);
	}
	
	// 댓글 객체 생성 메서드
	public static Reply createReply(Post post, Member member) {
		Reply reply = new Reply();
		reply.setPost(post);
		reply.setMember(member);
		return reply;
	}
}

