package com.alice.project.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.exception.PasswordWrongException;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional //로직을 처리하다가 에러 발생 시 변경된 데이터를 로직 수행 이전 상태로 콜백
@RequiredArgsConstructor // final 필드 생성자 생성해줌
public class MemberService implements UserDetailsService { // MemberService가 UserDetailService를 구현

	private final MemberRepository memberRepository;
	PasswordEncoder passwordEncoder;

	public Member saveMember(Member member) {
		return memberRepository.save(member); // insert
	}

	public Member findById(String id) {
		return memberRepository.findById(id);
	}
	
	
	// id 중복테스트
	public int checkIdDuplicate(String id) {
		boolean check = memberRepository.existsById(id);
		if (check) {
			return 1; // 아이디 중복이면 1
		} else {
			return 0; // 사용 가능 아이디면 0
		}
	}

	// id 찾기
	public Member findId(String name, String mobile, String email) {
		Member member = memberRepository.findByNameAndMobileAndEmail(name, mobile, email);
		return member;
	}

	// 비밀번호 찾기
	public Member findPwd(String id, String name, String mobile) {
		Member member = memberRepository.findByIdAndNameAndMobile(id, name, mobile);

		return member;
	}

	public Member findByNum(Long num) {
		Member member = memberRepository.findByNum(num);
		return member;
	}

	// 비밀번호 재설정
	public Member updateMember(Member member) {

		return memberRepository.save(member);
	}


	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException { // 로그인 할 유저의 id를 파라미터로 전달받음
		Member member = memberRepository.findById(id);

		if (member == null) {
			throw new UsernameNotFoundException(id);
		}
		/*
		 * UserDetail을 구현하고 있는 User 객체 반환 User객체를 생성하기 위해 생성자로 회원의 아이디, 비밀번호, status를
		 * 파라미터로 넘겨 줌
		 */
		return User.builder().username(member.getId()).password(member.getPassword()).roles(member.getStatus().toString())
				.build();
	}

}
