package com.alice.project.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.alice.project.web.MemberDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Slf4j
public class Member {

	@Id
	@GeneratedValue // (strategy = GenerationType.IDENTITY)
	@Column(name = "member_num")
	private Long num; // 회원번호

	@Column(unique = true) // 유니크 제약
	private String id; // 회원 아이디

	private String pwd; // 회원 비밀번호
	private String name; // 회원 이름
	private LocalDate birth; // 회원 생일

	@Enumerated(EnumType.STRING)
	private Gender gender; // 회원 성별 [MALE, FEMALE, UNKNOWN]

	private String email; // 회원 이메일
	private String mobile; // 회원 전화번호
	private String mbti; // 회원 MBTI
	private String wishlist; // 회원 위시리스트

	private LocalDate regDate; // 회원 가입일자

	private String profileImg; // 프로필사진 저장된 파일명(ex. 회원아이디.jpeg)

	/*
	 * private String originName; private String saveName;
	 */

	private final Long reportCnt = 0L; // 신고 누적횟수 (default=0)

	@Enumerated(EnumType.STRING)
	private Status status; // 사용자 상태 [USER_IN, USER_OUT, ADMIN]

	@OneToMany(mappedBy = "member")
	private List<Post> posts = new ArrayList<>(); // 사용자가 쓴 게시물

	@OneToMany(mappedBy = "member")
	private List<Reply> replies = new ArrayList<>(); // 사용자가 쓴 댓글

	@OneToMany(mappedBy = "member")
	private List<Calendar> calendars = new ArrayList<>(); // 사용자가 생성한 일정

	@OneToMany(mappedBy = "member")
	private List<Report> reports = new ArrayList<>(); // 사용자가 한 신고리스트

	@OneToMany(mappedBy = "member")
	private List<Community> communities = new ArrayList<>(); // 사용자가 만든 커뮤니티 리스트

	@OneToMany(mappedBy = "member")
	private List<Message> messages = new ArrayList<>(); // 사용자가 보낸 쪽지 리스트

	@OneToMany(mappedBy = "member")
	private List<FriendsGroup> groups = new ArrayList<>(); // 사용자가 생성한 그룹 리스트

	@OneToMany(mappedBy = "member")
	private List<Friend> friends = new ArrayList<>(); // 사용자가 등록한 친구 리스트

	@PrePersist
	public void reg_date() {
		this.regDate = LocalDate.now();
	}

	// 필수값만 가진 생성자
//	@Builder
//	public Member(String id, String pwd, String name, LocalDate birth, Gender gender, String email, String mobile,
//			LocalDate regDate, Status status) {
//		super();
//		this.id = id;
//		this.pwd = pwd;
//		this.name = name;
//		this.birth = birth;
//		this.gender = gender;
//		this.email = email;
//		this.mobile = mobile;
//		this.regDate = regDate;
//		this.status = status;
//	}

	// 필수값만 가진 회원객체 생성 메서드 (정적 팩토리 메서드)
	public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberDto.getId());
		String password = passwordEncoder.encode(memberDto.getPwd());
		member.setPwd(password);
		member.setName(memberDto.getName());
		member.setBirth(memberDto.getBirth());
		member.setGender(memberDto.getGender());
		log.info("이거시 젠더다" + memberDto.getGender());
		member.setEmail(memberDto.getEmail());
		member.setMobile(memberDto.getMobile());
		member.setMbti(memberDto.getMbti());
		member.setWishlist(memberDto.getWishList());
		member.setRegDate(member.getRegDate());
		member.setProfileImg(memberDto.getSaveName());
//		member.setOriginName(memberDto.getOriginName());
		member.setStatus(Status.USER_IN);
		return member;

	}

}