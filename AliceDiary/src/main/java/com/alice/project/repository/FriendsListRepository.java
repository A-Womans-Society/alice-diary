package com.alice.project.repository;
//db와 접근하기 위해 필요 서비스로부터 요청 받고 한번에 커밋하게 됨.
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alice.project.domain.Member;
import com.alice.project.web.FriendsDto;

@Repository
public interface FriendsListRepository extends JpaRepository<FriendsDto, Long> {

	// 친구 전체목록 조회
	List<FriendsDto> findAll();

	// 친구목록 id로 검색
	Member findMemberByid(String id);
}

