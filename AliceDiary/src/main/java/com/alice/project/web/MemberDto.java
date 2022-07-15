package com.alice.project.web;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberDto {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String id;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
	private String password;

	private String confirmPassword;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;

	@NotNull(message = "생년월일은 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;

	@NotBlank(message = "전화번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
	private String mobile;

	private String mbti;

	private String wishList;
	
	private String saveName;
	
	private MultipartFile profileImg;
	

	public MemberDto(@NotBlank(message = "아이디는 필수 입력 값입니다.") String id,
			@NotEmpty(message = "비밀번호는 필수 입력 값입니다.") @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.") String password,
			String confirmPassword, @NotBlank(message = "이름은 필수 입력 값입니다.") String name,
			@NotNull(message = "생년월일은 필수 입력 값입니다.") LocalDate birth, Gender gender,
			@NotEmpty(message = "이메일은 필수 입력 값입니다.") @Email(message = "이메일 형식으로 입력해주세요.") String email,
			@NotBlank(message = "전화번호는 필수 입력 값입니다.") @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.") String mobile,
			String mbti, String wishList, String saveName, 
			MultipartFile profileImg) {
		super();
		this.id = id;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.mbti = mbti;
		this.wishList = wishList;
		this.saveName = saveName;
		this.profileImg = profileImg;
	}
	
	public MemberDto(Member member) {
		this.id = member.getId();
		this.password = member.getPassword();
		this.name = member.getName();
		this.birth = member.getBirth();
		this.gender = member.getGender();
		this.email = member.getEmail();
		this.mobile = member.getMobile();
		this.mbti = member.getMbti();
		this.wishList = member.getWishlist();
		this.saveName = member.getProfileImg();
	}
	
	//비밀번호 재설정을 위한 생성자
	public MemberDto(Member member, String newPwd) {
		
		this.id = member.getId();
		this.password = newPwd;
		this.name = member.getName();
		this.birth = member.getBirth();
		this.gender = member.getGender();
		this.email = member.getEmail();
		this.mobile = member.getMobile();
		this.mbti = member.getMbti();
		this.wishList = member.getWishlist();
		this.saveName = member.getProfileImg();
		

	}

	//프로필 수정을 위한 생성자
	public MemberDto(Member member, String id, String name, LocalDate birth, String email, String mobile,
			String mbti, String wishList) {
		super();
		this.id = id;
		this.name = member.getName();
		this.birth = member.getBirth();
		this.email = member.getEmail();
		this.mobile = member.getMobile();
		this.mbti = member.getMbti();
		this.wishList = member.getWishlist();
	}
	
	
	
	
	
}
