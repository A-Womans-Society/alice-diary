package com.alice.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.web.MemberDto;

@SpringBootTest

@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberDto memberDto = new MemberDto();
		memberDto.setId("exampleID");
		memberDto.setPassword("5678");
		memberDto.setName("이순신");
		memberDto.setBirth(LocalDate.of(1925, 10, 12));
		memberDto.setGender(Gender.MALE);
		memberDto.setEmail("test@gmail.com");
		memberDto.setMobile("010-1234-5678");
		memberDto.setMbti("INFP");
		memberDto.setWishList("아무거나123");
		return Member.createUser(memberDto, passwordEncoder);
	}
	
	
	//Junit의 Assertions 클래스의 assertEquals 메소드를 이용하여 저장하려고 요청했던 값과 실제 저장된 데이터를 비교
	@Test
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		Member member = createMember();
		Member savedMember = memberService.saveMember(member);
		
		assertEquals(member.getId(), savedMember.getId());
		assertEquals(member.getPassword(), savedMember.getPassword());
		assertEquals(member.getName(), savedMember.getName());
		assertEquals(member.getBirth(), savedMember.getBirth());
		assertEquals(member.getGender(), savedMember.getGender());
		assertEquals(member.getEmail(), savedMember.getEmail());
		assertEquals(member.getMobile(), savedMember.getMobile());
		assertEquals(member.getMbti(), savedMember.getMbti());
		assertEquals(member.getWishlist(), savedMember.getWishlist());
	}
	
	@Test
	@DisplayName("중복 회원 가입 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		memberService.saveMember(member1);
		
		Throwable e = assertThrows(IllegalStateException.class, ()->{memberService.saveMember(member2);});
		
		assertEquals("이미 가입된 회원입니다.", e.getMessage());
	}
}
