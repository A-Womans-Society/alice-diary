package com.alice.project.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.service.FriendsListService;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class FriendsListRepositoryTest {

	@Autowired
	FriendsListRepository flr;
	@Autowired
	FriendsListService fls;
	
//	@Test
//	@DisplayName("회원 저장 테스트")
//	public void saveFriend(Member member) {
//
//		Member member = new Member(member.getId(),
//				member.getName(), null, null, null, null, null, null, null);

//		member.setName("김민경");
//		member.setMobile("010-1111-1111");
//		member.setBirth(LocalDate.of(2001, 05, 19));
//		member.setEmail("mki1105@naver.com");
//		member.setGender(Gender.FEMALE);
//		member.setMbti("ENFJ");
//		member.setProfileImg("hoho");
//		그룹
//		Member saveFriend = flr.save(member1);
//		System.out.println(saveFriend.toString());

	}

