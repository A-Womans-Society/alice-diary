package com.alice.project.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alice.project.service.FriendsGroupService;

import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.alice.project.web.UserDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@ToString
@Slf4j
@EqualsAndHashCode(of = "num")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "SEQ_MEMBER_NUM", initialValue = 1, allocationSize = 1)
	@Column(name = "member_num")
	private Long num; // 회원번호

	@Column(unique = true) // 유니크 제약
	private String id; // 회원 아이디

	private String password; // 회원 비밀번호
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

//	@OneToMany(mappedBy="member")
//	private List<Message> messages = new ArrayList<>(); // 사용자가 보낸 쪽지 리스트

	@OneToMany(mappedBy = "member")
	private List<FriendsGroup> groups = new ArrayList<>(); // 사용자가 생성한 그룹 리스트

//	@OneToMany(mappedBy = "member")
//	private List<Friend> friends = new ArrayList<>(); // 사용자가 등록한 친구 리스트

	@PrePersist
	public void reg_date() {
		this.regDate = LocalDate.now();
	}

	// 필수값만 가진 생성자
	@Builder
	public Member(String id, String password, String name, LocalDate birth, Gender gender, String email, String mobile,
			LocalDate regDate, Status status) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.regDate = regDate;
		this.status = status;
	}

	public static Member createMember() {
		Member member = new Member("noFriend");

		return member;
	}

	public Member(Long groupNum, FriendsGroupService fgs) {
		this.groups.add(fgs.getGroupByNum(groupNum));
	}

	@Builder
	public Member(String name) {
		super();
		this.name = name;
	}

	@Builder
	public Member(List<FriendsGroup> groups) {
		super();
		this.groups = groups;
	}

	@Builder
	public Member(String id, String password, String name, LocalDate birth, Gender gender, String email, String mobile,
			String mbti, String wishlist, String profileImg, Status status) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.mbti = mbti;
		this.wishlist = wishlist;
		this.regDate = LocalDate.now();
		this.profileImg = profileImg;
		this.status = Status.USER_IN;
	}

	@Builder
	public Member(String id, String password, String name, LocalDate birth, Gender gender, String email, String mobile,
			String mbti, String wishlist, LocalDate regDate, String profileImg, Status status) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.mbti = mbti;
		this.wishlist = wishlist;
		this.regDate = regDate;
		this.profileImg = profileImg;
		this.status = status;
	}

	@Builder
	public Member(Long num, String id, String password, String name, LocalDate birth, Gender gender, String email,
			String mobile, String mbti, String wishlist, LocalDate regDate, String profileImg, Status status) {
		super();
		this.num = num;
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.mbti = mbti;
		this.wishlist = wishlist;
		this.regDate = regDate;
		this.profileImg = profileImg;
		this.status = status;
	}

	// 회원객체 생성 메서드 (정적 팩토리 메서드)
	public static Member createMember(String id, String pwd, String name, LocalDate birth, Gender gender, String email,
			String mobile, String mbti, String wishlist, String profileImg, Status status) {
		Member member = new Member(id, pwd, name, birth, gender, email, mobile, mbti, wishlist, profileImg, status);
		return member;
	}

	@Builder
	public Member(Status status) {
		this.status = Status.USER_OUT;
	}

	// 필수값만 가진 회원객체 생성 메서드 (정적 팩토리 메서드)
	public static Member createMember(UserDto memberDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(memberDto.getId(), passwordEncoder.encode(memberDto.getPassword()),
				memberDto.getName(), memberDto.getBirth(), memberDto.getGender(), memberDto.getEmail(),
				memberDto.getMobile(), memberDto.getMbti(), memberDto.getWishList(), LocalDate.now(),
				memberDto.getSaveName(), Status.USER_IN);
		return member;
	}

	public static Member createMember(Long num, UserDto memberDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(num, memberDto.getId(), passwordEncoder.encode(memberDto.getPassword()),
				memberDto.getName(), memberDto.getBirth(), memberDto.getGender(), memberDto.getEmail(),
				memberDto.getMobile(), memberDto.getMbti(), memberDto.getWishList(), LocalDate.now(),
				memberDto.getSaveName(), Status.USER_IN);
		return member;
	}

	// 회원 내보내기 메서드
	public static Member changeMemberOut(Member member) {
		member.status = Status.USER_OUT;
		log.info("엔티티 changeMemberOut메서드에서 status바꾸기 : " + member.status);
		return member;
	}

	// 회원 복구하기 메서드
	public static Member changeMemberIn(Member member) {
		member.status = Status.USER_IN;
		log.info("엔티티 changeMemberIn메서드에서 status바꾸기 : " + member.status);
		return member;
	}
}
