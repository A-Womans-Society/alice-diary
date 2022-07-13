package com.alice.project.web;

import com.alice.project.domain.Member;

import lombok.Getter;
import lombok.Setter;
// 친구 검색 했을 때 보이는 Dto
@Getter @Setter
public class AddFriendDto {

	private String memId; // 친구 id
	private String profileImg; // 친구 프로필
	private Long num; // 친구 번호
	private Long adder; // 등록하는 친구 회원번호
	private Long addee; // 등록되는 친구 회원번호
//	private Member member; // 친구 등록하는 회원 객체
	
	// 
	public AddFriendDto(String memId, String profileImg, Long num, Long adder, Long addee, Member member) {
		super();
		this.memId = memId;
		this.profileImg = profileImg;
		this.num = num;
		this.adder = adder;
		this.addee = addee;
//		this.member = member;
	}

}
