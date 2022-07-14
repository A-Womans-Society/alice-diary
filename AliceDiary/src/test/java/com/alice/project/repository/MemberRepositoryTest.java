package com.alice.project.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.domain.Status;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestPropertySource(locations="classpath:application.properties")
class MemberRepositoryTest {
	
	@Autowired MemberRepository memberRepository;
	
	// 더미데이터 넣기 
	@Rollback(false)
	public void createMemberList() {
		for (int i = 1; i <= 9; i++) {
			Member member = Member.createMember("id"+i, "pwd"+i*2, "이름"+i, LocalDate.now(), Gender.FEMALE, "aaa@fff"+i+".com", "010-5654-564"+i, "enfp", "화장품", "mdjf.jpg", Status.USER_IN);
			Member savedMember = memberRepository.save(member);
		}
	}
	
	@Test
	@DisplayName("회원 등록 테스트")
	public void findByMemberTest() {
		this.createMemberList();
		Member  member = memberRepository.findById("이름2");
		
			log.info(member.toString());
			
	}

}
