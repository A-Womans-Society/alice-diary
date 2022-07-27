package com.alice.project.web;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alice.project.repository.MemberRepository;
import com.alice.project.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDtoValidator implements Validator {

	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.isAssignableFrom(UserDto.class);
	}

	@Override
	public void validate(@Valid Object target, Errors errors) {
		log.info("들어오니????????????????????");
		UserDto userDto = (UserDto) target;
		if (memberRepository.existsByEmail(userDto.getEmail())) {
			log.info("겹친다?????????????????????????");
			errors.rejectValue("email", "invalid.email", new Object[] { userDto.getEmail() }, "이미 사용중인 이메일입니다.");
		}
		if (memberService.checkIdDuplicate(userDto.getId()) == 1) {
			log.info("memberService.checkIdDuplicate(userDto.getId()) = "
					+ memberService.checkIdDuplicate(userDto.getId()));
			errors.rejectValue("id", "invalid.id", new Object[] { userDto.getId() }, "이미 사용중인 아이디입니다.");
		}
	}

}