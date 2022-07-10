package com.alice.project.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;
// 안적은 컬럼은 참고해서 다시 수정하기
@Entity
@Getter
@Setter
@Table(name="member")
@ToString
public class Member {
	@Id
	@Column(name = "mem_num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long memNum; // 회원 고유번호
	
	@Column(name = "mem_id", unique = true)
	private String memId; // 아이디
	
	@Column(nullable = false)
	private String name; // 회원 이름
	
	@Column(nullable = false)
	private String mobile; // 회원 전화번호
	
	@Column(nullable = false)
	private LocalDate birth; // 회원 생년월일
	
	@Column(nullable = false)
	private String gender; // 회원 성별
	
	@Column(nullable = false)
	private String email; // 회원 이메일
	
	@Column(nullable = false)
	private String group; // 회원 그룹
	
	@Column(nullable = true)
	private String mbti; // mbti
	
	@Column(nullable = true)
	private String wishlist; // 위시리스트
	
	@Column(name = "profile_img", nullable = true)
	private String profileImg; // 프로필 사진
//비밀번호, 가입날짜, 신고횟수, 상태
	

	
}

