package com.alice.project.web;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.alice.project.domain.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String id;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	private String password;


	private String confirmPassword;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;

//	@NotBlank(message = "생년월일은 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;

//	@NotEmpty(message = "성별은 필수 입력 값입니다.")
	private Gender gender;

	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;

	@NotBlank(message = "전화번호는 필수 입력 값입니다.")
	private String mobile;

	private String mbti;

	private String wishList;
}
