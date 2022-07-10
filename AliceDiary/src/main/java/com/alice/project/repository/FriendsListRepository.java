package com.alice.project.repository;

//db와 접근하기 위해 필요 서비스로부터 요청 받고 한번에 커밋하게 됨.
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alice.project.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
//public interface FriendsListRepository extends JpaRepository<Member, Long> {
@RequiredArgsConstructor
public class FriendsListRepository {

	private final EntityManager em;

	// 회원 저장
	public void save(Member member) {
		em.persist(member);
	}

	// 친구 전체목록 조회
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	// 친구목록 id로 검색
	public Member findOne(String memId) {
		return em.find(Member.class, memId);
	}
}
// 친구검색 나중에 활용하기
// public List<Member> findByMemId(String memId); // 친구목록 id로 검색
// public List<Member> findByName(String name) {
// return em.createQuery("select m from Member m where m.name = :name",
// Member.class).setParameter("name", name).getResultList();
