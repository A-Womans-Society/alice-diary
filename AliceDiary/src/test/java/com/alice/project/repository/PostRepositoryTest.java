package com.alice.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.alice.project.domain.Community;
import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.domain.PostType;
import com.alice.project.domain.Status;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class PostRepositoryTest {
	
	@Autowired PostRepository postRepository;
	
	// 더미데이터 넣기 
//	public void createPostList() {
//		for (int i = 1; i <= 9; i++) {
//			Member member = Member.createMember("id"+i, "pwd"+i*2, "이름"+i, LocalDate.now(), Gender.FEMALE, "aaa@fff"+i+".com", "010-5654-564"+i, "enfp", "화장품", "mdjf.jpg", Status.USER_IN);
//			Community community = Community.createCommunity(member);
//			Post post = Post.createPost("제목1", LocalDateTime.now(), LocalDateTime.now(), 
//					"내용1", 0L, PostType.OPEN, member, community);
//			Post savedPost = postRepository.save(post);
//		}
//	}
	
//	@Test
//	@DisplayName("회원 등록 테스트")
//	public void findByPostTest() {
//		this.createPostList();
//		List<Member>  memberList = postRepository.findByPostTitle("이름2");
//		for (Member member : memberList) {
//			log.info(member.toString());
//			
//		}
//	}

}
