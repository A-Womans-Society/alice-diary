package com.alice.project.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alice.project.domain.Member;
import com.alice.project.service.MemberService;
import com.alice.project.web.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	private final PasswordEncoder passwordEncoder;

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
	public String memberForm(@ModelAttribute("memberDto") @Valid UserDto memberDto, BindingResult bindingResult,
			HttpSession session) {
		log.info("POST 나옴");
		log.info("!!!!!!!!!!!!!!!!!!!!!!!!1memberDto의 name " + memberDto.getName());
		if (bindingResult.hasErrors()) {
			log.info("에러 발생!");
			return "login/registerForm";
		}
		// if (!memberDto.getProfileImg().getOriginalFilename().equals("")) { // 프로필 사진이
		// 있으면
//			String originName = memberDto.getProfileImg().getOriginalFilename();
//			String saveName = memberDto.getId() + "." + originName.split("\\.")[1];
//			String savePath = session.getServletContext().getRealPath("c:\\Temp\\upload");
//
//			try {
//				memberDto.getProfileImg().transferTo(new File(savePath, saveName));
//				memberDto.setSaveName(saveName);
//
//				Member member = Member.createMember(memberDto, passwordEncoder);
//				memberService.saveMember(member);
//
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		// } else { // 프로필 사진이 없으면
		Member member = Member.createMember(memberDto, passwordEncoder);
		log.info("member의 Name== " + member.getName());
		memberService.saveMember(member);
//		}

		return "redirect:/";
	}

	// ID 중복체크 PostMapping
	@PostMapping("/register/idCheck")
	@ResponseBody
	public int checkIdDuplication(@RequestParam(value = "id") String id) {
		log.info("userIdCheck 진입");
		int check = memberService.checkIdDuplicate(id);
		return check;
	}

	// 로그인에러 GetMapping
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "redirect:/";
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
	public String updatePwd(@ModelAttribute(value = "memberDto") Member m, Model model) {
		log.info("비밀번호 재설정 GET 진입");
		log.info("Member m === " + m);
		UserDto mdto = new UserDto(m);
		Long num = m.getNum();
		model.addAttribute("memberDto", mdto);
		model.addAttribute("num", num);
		return "login/updatePwd";
	}

	// 비밀번호 재설정 Post
	@PostMapping(value = "/login/savePwd")
	public String updatePwd(UserDto memberDto, @RequestParam("num") Long num) {
		log.info("비밀번호 재설정 POST 진입");
		Member member = memberService.findByNum(num);
		log.info("비밀번호 재설정 전 Member : " + member);
		UserDto userDto = new UserDto(member, memberDto.getPassword());
		member = Member.createMember(num, userDto, passwordEncoder);
		member = memberService.updateMember(member);
		log.info("비밀번호 재설정 후 Member : " + member);

		return "redirect:/";
	}

	/*
	 * //Email 인증
	 * 
	 * @PostMapping("/register/emailCheck")
	 * 
	 * @ResponseBody public void checkEmailKey(String email) throws Exception {
	 * logger.info("EmailCheck 진입"); logger.info("전달받은 email:"+email);
	 * memberService.sendSimpleMessage(email); logger.info("email 보냄!");
	 * 
	 * }
	 */

}