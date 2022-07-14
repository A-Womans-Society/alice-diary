package com.alice.project.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.ToString;

@Entity
@Table(name="community")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA 사용을위해 기본 생성자 생성은 필수 =  protected Community() { }
@Getter
@ToString
public class Community {
	
	@Id @GeneratedValue
	@Column(name="community_num")
	private Long num; // 커뮤니티 번호
	private String name; // 커뮤니티 이름
	private String memberList; // 커뮤니티 참여회원 리스트
	private LocalDate regDate; // 커뮤니티 생성일자
	private String description; // 커뮤니티 설명
	
	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name="member_num")
	private Member member; // 커뮤니티 생성 회원 객체
	
	@OneToMany(mappedBy="community")
	private List<Post> posts = new ArrayList<>(); // 해당 커뮤니티 소속 게시물리스트
	
	/* (넣자구 하면 주길꺼져...?ㅎㅅㅎ) */
	// private String thumbnail; // 커뮤니티 섬네일 이미지
	
	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getCommunities().add(this);
	}
	
	// 커뮤니티 객체 생성 메서드
	public static Community createCommunity(Member member) {
		Community community = new Community();
		community.setMember(member);
		return community;
	}
}
