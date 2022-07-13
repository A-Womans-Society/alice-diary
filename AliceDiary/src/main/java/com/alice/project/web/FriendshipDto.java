package com.alice.project.web;

import java.time.LocalDate;
import com.alice.project.domain.Gender;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
// 추가된 친구 목록 보여줄 조회 페이지에 전달할 dto
public class FriendshipDto {
	private Long num; // 회원번호
	private String id;// 회원 아이디
	private String name; // 회원 이름
	private String mobile; // 회원 전화번호
	private LocalDate birth;// 회원 생년월일
	private Gender gender; // 회원 성별
	private String email; // 회원 이메일
	
	private String groupName; // 회원 그룹 이름(기본그룹으로 default)

}
