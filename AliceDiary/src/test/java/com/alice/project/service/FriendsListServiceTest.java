package com.alice.project.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Member;
import com.alice.project.repository.FriendsListRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
@TestPropertySource(locations="classpath:application-test.properties")
class FriendsListServiceTest {
	
	private final FriendsListRepository flr;
//	@Test
//	public void createFriend() {
//		//given
//		Member member = new Member();
//		member.setName("mk");
//		// em.persist(member);
//		
//		//when
//		Member saveMember = flr.save(member);
//		
//		//then

		
	}

