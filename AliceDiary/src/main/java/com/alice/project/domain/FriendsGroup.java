package com.alice.project.domain;

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
@Table(name="friendsGroup")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class FriendsGroup {
	
	@Id @GeneratedValue
	@Column(name="group_num")
	private Long num; // 그룹 번호
	private String groupName = "기본그룹"; // 그룹이름 (default="기본그룹")
//	private Long groupCreatorNum; // 그룹생성 회원번호
	
	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
	@JoinColumn(name="member_num")
	private Member member; // 그룹 생성 회원 객체
	
	@OneToMany(mappedBy="group")
	private List<Friend> friends = new ArrayList<>(); // 해당 그룹에 속한 친구객체 리스트
	
	// 연관관계 메서드 (양방향관계)
	public void setMember(Member member) {
		this.member = member;
		member.getGroups().add(this);
	}

	
}


