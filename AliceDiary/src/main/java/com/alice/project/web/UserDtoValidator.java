package com.alice.project.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDtoValidator implements Validator {

	private final MemberRepository memberRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.isAssignableFrom(UserDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDto userDto = (UserDto) target;
		if (memberRepository.existsByEmail(userDto.getEmail())) {
			errors.rejectValue("email", "invalid.email", new Object[] { userDto.getEmail() }, "이미 사용중인 이메일입니다.");
		}

	}

}
