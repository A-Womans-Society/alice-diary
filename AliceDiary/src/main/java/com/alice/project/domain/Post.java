package com.alice.project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString
public class Post {
	
	@Id @GeneratedValue
	@Column(name="post_num")
	private Long num; // 게시물번호
	private String title; // 게시물 제목
	private LocalDateTime postDate; // 게시물 작성일자
	private LocalDateTime updateDate; // 게시물 수정일자
	private String content; // 게시물 내용
	private Long viewCnt = 0L; // 게시물 조회수 (default=0)
	
	@Enumerated(EnumType.STRING)
	private PostType postType; // 게시물 종류 [NOTICE, OPEN, CUSTOM]
	
	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name="member_num")
	private Member member; // 작성회원 객체
	
	/* community가 null일 수 있음 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="community_num")
	private Community community; // 소속 커뮤니티 객체
	
	/* replies가 null일 수 있음 */
	@OneToMany(mappedBy="post")
	private List<Reply> replies = new ArrayList<>(); // 게시물 소속 댓글 리스트
	
	/* files가 null일 수 있음 */
//	@OneToMany(mappedBy="post")
//	private List<AttachedFile> files = new ArrayList<>(); // 게시물 소속 첨부파일 리스트
	

	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getPosts().add(this);
	}
	
	public Post(String title, LocalDateTime postDate, LocalDateTime updateDate, 
			String content, Long viewCnt, PostType postType, Member member, 
			Community community) {
		this.title = title;
		this.postDate = postDate;
		this.updateDate = updateDate;
		this.content = content;
		this.viewCnt = viewCnt;
		this.postType = postType;
		this.member = member;
		this.community = community;
	}
	
	// 게시물 객체 생성 메서드
	public static Post createPost(String title, LocalDateTime postDate, 
			LocalDateTime updateDate, String content, Long viewCnt,
			PostType postType, Member member, Community community) {
		Post post = new Post(title, postDate, updateDate, content, viewCnt,
				postType, member, community);
		return post;
	}
	
}
