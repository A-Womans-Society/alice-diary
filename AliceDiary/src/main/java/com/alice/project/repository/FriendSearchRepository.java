package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.web.FriendsDto;
@Repository
public interface FriendSearchRepository extends JpaRepository<FriendsDto, Long> {
	
	// 친구 아이디 검색
	List<FriendsDto> findBymemId(String memId);
	// 친구 전화번호 검색
	List<FriendsDto> findByMobile(String mobile);
	// 친구 이름 검색
	List<FriendsDto> findByname(String name);

	// return em.createQuery("select m from Member m where m.name = :name",
	// Member.class).setParameter("name", name).getResultList();
}
