package com.alice.project.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alice.project.config.CurrentMember;
import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;
import com.alice.project.service.MemberService;
import com.alice.project.web.UserDto;
import com.alice.project.web.UserDtoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final UserDtoValidator userDtoValidator;
	private final MemberRepository memberRepository;

	// 먼저 Validator로 인증해주는 메서드
	@InitBinder("memberDto")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(userDtoValidator);
	}

	// 약관동의GetMapping
	@GetMapping(value = "/agree")
	public String memberForm() {
		return "login/agreeForm";
	}

	// 회원가입 GetMapping
	@GetMapping(value = "/register")
	public String memberForm(Model model) {
		log.info("GET 나옴");
		model.addAttribute("memberDto", new UserDto());
		return "login/registerForm";
	}

	// 회원가입 PostMapping 가입 성공 후 로그인 페이지로 이동
	@PostMapping(value = "/register")
	public String memberForm(@ModelAttribute("memberDto") @Valid UserDto userDto, BindingResult bindingResult,
			Model model) {
		log.info("POST 나옴");
		log.info("userDto.getProfileImg() = " + userDto.getProfileImg());
		if (bindingResult.hasErrors()) {
			log.info("에러 발생!");
			return "login/registerForm";
		}
		memberService.processNewMember(userDto);
		return "redirect:/";
	}

	@GetMapping("/check-email-token")
	public String checkEmailToken(String token, String email, Model model) {
		Member member = memberRepository.searchByEmailForToken(email);
		String view = "login/checked-email";
		if (member == null) {
			model.addAttribute("error", "wrong.email");
			return view;
		}

		if (!member.isValidToken(token)) {
			model.addAttribute("error", "wrong.token");
			return view;
		}
		log.info("token : " + token);
		memberService.completeSignUp(member);
		return view;
	}

	@GetMapping("/check-email")
	public String checkEmail(@CurrentMember Member member, Model model) {
		model.addAttribute("email", member.getEmail());
		return "login/check-email";
	}

	@GetMapping("/resend-confirm-email")
	public String resendConfirmEmail(@CurrentMember Member member, Model model) {
		if (!member.canSendConfirmEmail()) {
			model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
			model.addAttribute("email", member.getEmail());
			return "login/check-email";
		}

		memberService.sendSignUpConfirmEmail(member);
		return "redirect:/";
	}
	
	// ID 중복체크 PostMapping
	@PostMapping("/register/idCheck")
	@ResponseBody
	public String checkIdDuplication(String id) {
		log.info("userIdCheck 진입");
		String check = String.valueOf(memberService.checkIdDuplicate(id));
		log.info("check== " + check);
		return check;
	}

	// Id찾기 Get
	@GetMapping(value = "/login/findId")
	public String findId() {
		log.info("findId GET진입");
		return "login/findId";
	}

	// Id찾기 Post
	@PostMapping(value = "/login/findId")
	@ResponseBody
	public Member findId(String name, String mobile, String email) {
		log.info("findId POST진입");
		log.info("findId POST진입" + name);
		log.info("findId POST진입" + mobile);
		log.info("findId POST진입" + email);

		Member member = memberService.findId(name, mobile, email);
		return (member == null) ? null : member;
	}

	// 비밀번호 찾기 Get
	@GetMapping(value = "/login/findPwd")
	public String findPwd(Model model) {
		log.info("비밀번호 찾기 GET 진입");
		model.addAttribute("userDto", new UserDto());
		return "login/findPwd";
	}

	// 비밀번호 찾기 Post
	@PostMapping(value = "/login/findPwd")
	public String findPwd(UserDto userDto, RedirectAttributes re, Model model) {
		log.info("비밀번호 찾기 POST 진입");
		Member member = memberService.findPwd(userDto.getId(), userDto.getName(), userDto.getMobile());
		if (member != null) {
			re.addFlashAttribute("member", member);
			return "redirect:/login/updatePwd";
		} else {
			model.addAttribute("msg", "존재하지 않는 유저입니다.");
			return "/login/findPwd";

		}
	}

	// 비밀번호 재설정 Get
	@GetMapping(value = "/login/updatePwd")
	public String updatePwd(Member member, Model model) {
		log.info("비밀번호 재설정 GET 진입");
		log.info("Member name === " + member.getName());
		UserDto userDto = new UserDto();
		Long num = member.getNum();
		model.addAttribute("userDto", userDto);
		model.addAttribute("num", num);
		return "login/updatePwd";
	}

	// 비밀번호 재설정 Post
	@PostMapping(value = "/login/savePwd")
	public String updatePwd(UserDto userDto, Long num) {
		log.info("비밀번호 재설정 POST 진입");
		Member member = memberService.findByNum(num);
		log.info("비밀번호 재설정 전 Member Password : " + member.getPassword());
		UserDto uDto = new UserDto(member, userDto.getPassword());
		member = Member.createMember(num, uDto, passwordEncoder);
		member = memberService.updateMember(member);
		log.info("비밀번호 재설정 후 Member Password : " + member.getPassword());

		return "redirect:/";
	}
	
}