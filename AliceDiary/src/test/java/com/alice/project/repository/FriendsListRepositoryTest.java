package com.alice.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Member;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class FriendsListRepositoryTest {

	@Autowired
	FriendsListRepository flr;

	@Test
	@DisplayName("회원 저장 테스트")
	public void saveFriends() {

		Member member = new Member();

		member.setMemId("ming");
		member.setName("김민경");
		member.setMobile("010-1111-1111");
		member.setBirth(LocalDate.now());
		member.setEmail("mki1105@naver.com");
		member.setGender("여");
		member.setGroup("고등학교");
		member.setMbti("ENFJ");
		member.setProfileImg("hoho");

		//Member saveFriends1 = flr.save(member);
		//System.out.println(saveFriends1.toString());

	}
}
