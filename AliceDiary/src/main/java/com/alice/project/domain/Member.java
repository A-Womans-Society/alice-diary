package com.alice.project.domain;
//회원 정보를 저장하는 클래스

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.alice.project.web.MemberDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name="mem_num")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long num;
	
	@Column(unique = true)
	private String id;
	
	private String password;

	private String name;
	
	private LocalDate birth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender; //사용자 성별[MALE, FEMALE, UNKNOWN]
	
	private String email;
	
	private String mobile;
	
	private String mbti;
	
	private String wishlist;
	
//	@Column(nullable = false)
	private LocalDateTime regDate;
	
	private String profileImg;
	
//	@Column(nullable = false)
	private Long reportCnt;
	
	@Enumerated(EnumType.STRING)
	private Status status; //사용자 상태[USER_IN, USER_OUT, ADMIN]
	
	

	@PrePersist
	public void reg_date() {
		this.regDate = LocalDateTime.now();
	}
	
	
	public static Member createUser(MemberDto memberDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberDto.getId());
		String password = passwordEncoder.encode(memberDto.getPassword());
		member.setPassword(password);
		member.setName(memberDto.getName());
		member.setBirth(memberDto.getBirth());
		member.setGender(memberDto.getGender());
		member.setEmail(memberDto.getEmail());
		member.setMobile(memberDto.getMobile());
		member.setMbti(memberDto.getMbti());
		member.setWishlist(memberDto.getWishList());
		return member;
		
	}



	





	

}
