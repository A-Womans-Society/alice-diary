package com.alice.project.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.alice.project.domain.Gender;
import com.alice.project.service.MemberService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
// 그룹 등록에 필요한 dto
public class FriendsGroupDto {
	@Autowired private MemberService ms;
	
	private Long num; // 회원번호
	private String id;// 회원 아이디
	private String name; // 회원 이름
	private String mobile; // 회원 전화번호
	private LocalDate birth;// 회원 생년월일
	private Gender gender; // 회원 성별
	private String email; // 회원 이메일
	
	private String groupName; // 회원 그룹 이름(기본그룹으로 default)
	
	
}
