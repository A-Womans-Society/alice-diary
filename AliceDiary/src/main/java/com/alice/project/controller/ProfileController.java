package com.alice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;
import com.alice.project.service.MemberService;
import com.alice.project.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
	
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final ProfileService profileService;

	
	@GetMapping(value = "/member/{id}")
	public String myProfile(@PathVariable String id, Model model) {
		Member member = profileService.findById(id);
		log.info("member=" + member);
		model.addAttribute("member", member);
		return "profile/myProfile";
	}
	
	@PostMapping(value="/member/{id}")
	public String updateProfile(@PathVariable String id, Model model) {
		log.info("POST 진입!!");
		Member member = profileService.findById(id);
		model.addAttribute("member", member);
		return "profile/updateProfile";
	}
	
	@PutMapping(value="/member/{id}")
	public Member updateProfile(@PathVariable @RequestParam String id, @RequestBody Member member) {
		
		Member updateMember = profileService.findMemById(id);
		
//		updateMember.ifPresent(selectMember-> {
//			selectMember.setName(member.getName());
//			selectMember.setMobile(member.getMobile());
//			selectMember.setBirth(member.getBirth());
//			selectMember.setEmail(member.getEmail());
//			selectMember.setMbti(member.getMbti());
//			selectMember.setWishlist(member.getWishlist());
//			
//			memberRepository.save(selectMember);
//		});
//		
//		
		
//		if (!memberDto.getProfileImg().getOriginalFilename().equals("")) {
//			String originName = memberDto.getProfileImg().getOriginalFilename();
//			String saveName = memberDto.getId() + "." + originName.split("\\.")[1];
//			String savePath = session.getServletContext().getRealPath("c:\\Temp\\upload");
//
//			try {
//				memberDto.getProfileImg().transferTo(new File(savePath, saveName));
//				memberDto.setSaveName(saveName);
//
//				Member member = Member.updateMember(memberDto);
//				memberService.saveMember(member);
//				log.info("수정 완료! member == " + member);
//
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			Member member = Member.updateMember(memberDto);
//			memberService.saveMember(member);
//		}
//		
		
		return updateMember;
	}
}
