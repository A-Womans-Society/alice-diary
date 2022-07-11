package com.alice.project.web;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.domain.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String id;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
	private String pwd;

	private String confirmPassword;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;

	@NotNull(message = "생년월일은 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;

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
}
