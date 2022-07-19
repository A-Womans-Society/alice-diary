package com.alice.project.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alice.project.domain.Member;
import com.alice.project.service.MemberService;
import com.alice.project.service.ProfileService;
import com.alice.project.web.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

	private final ProfileService profileService;
	private final PasswordEncoder passwordEncoder;
	private final MemberService memberService;

	//내 프로필 보기 GET
	@GetMapping(value = "/member/{id}")
	public String myProfile(@PathVariable String id, Model model) {
		Member member = profileService.findById(id);
		if (member.getProfileImg() == "default") {
			UserDto userDto = new UserDto();
			userDto.setSaveName("defualt");
			model.addAttribute("member", userDto);
		}
		model.addAttribute("member", member);
		return "profile/myProfile";
	}

	//내 프로필 수정 화면 GET
	@GetMapping(value = "/member/update/{id}")
	public String updateProfile(@PathVariable String id, Model model) {
		log.info("POST 진입!!");
		Member member = profileService.findById(id);
		model.addAttribute("member", member);
		return "profile/updateProfile";
	}

	//내 프로필 수정하기 POST
	@PostMapping(value = "/member/update/{id}")
	public String updateProfile(@PathVariable String id, @ModelAttribute("member") @Valid UserDto userDto,
			BindingResult bindingResult, Long num, HttpSession session) {
		log.info("프로필 수정 페이지 진입");
		log.info("member.num == " + num);
		if (!userDto.getProfileImg().getOriginalFilename().equals("")) {
			String originName = userDto.getProfileImg().getOriginalFilename();
			String saveName = id + "." + originName.split("\\.")[1];
			log.info("saveName == " + saveName);
			String savePath = session.getServletContext().getRealPath("c:\\Temp\\upload");

			try {
				userDto.getProfileImg().transferTo(new File(savePath, saveName));
				userDto.setSaveName(saveName);

				Member updateMember = profileService.findMemById(id);
				userDto.setPassword(updateMember.getPassword());
				userDto.setGender(updateMember.getGender());
				userDto.setSaveName(saveName);
//				userDto.setBirth(LocalDate.parse(newBirth, DateTimeFormatter.ISO_DATE));
				updateMember = Member.createMember(num, userDto, passwordEncoder);
				memberService.saveMember(updateMember);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Member updateMember = profileService.findMemById(id);
			userDto.setPassword(updateMember.getPassword());
			userDto.setGender(updateMember.getGender());
			userDto.setSaveName("default");
//			userDto.setBirth(LocalDate.parse(newBirth, DateTimeFormatter.ISO_DATE));
			updateMember = Member.createMember(num, userDto, passwordEncoder);
			memberService.saveMember(updateMember);
		}

		return "redirect:/member/{id}";
	}
	
	// 비밀번호 재설정 Get
		@GetMapping(value = "/member/{id}/editPwd")
		public String updatePwd(@PathVariable String id, Model model) {
			log.info("프로필 비밀번호 재설정 GET 진입");
			Member member = memberService.findById(id);
			model.addAttribute("member", member);
			return "profile/editPwd";
		}

		// 비밀번호 재설정 Post
		@PostMapping(value = "/member/editPwd")
		public String updatePwd(UserDto userDto,String id, RedirectAttributes re, Model model) {
			log.info("비밀번호 재설정 POST 진입");
			Member member = memberService.findById(id);
			log.info("비밀번호 재설정 전 Member Password : " + member.getPassword());
			re.addAttribute("id", member.getId());
			if(member.getPassword() != userDto.getPassword()) {
				model.addAttribute("msg", "현재 비밀번호가 일치하지 않습니다. 비밀번호를 다시 한번 확인해주세요.");
				return "redirect:/member/id/editPwd";
			}else if(userDto.getNewPwd() != userDto.getConfirmNewPwd()) {
				model.addAttribute("msg", "새로운 비밀번호가 일치하지 않습니다.");
				return "redirect:/member/id/editPwd";
			}else {
				UserDto uDto = new UserDto(member, userDto.getNewPwd());
				member = Member.createMember(id, uDto, passwordEncoder);
				member = memberService.updateMember(member);
				log.info("비밀번호 재설정 후 Member Password : " + member.getPassword());

			}

			return "/profile/myProfile";
		}

}
