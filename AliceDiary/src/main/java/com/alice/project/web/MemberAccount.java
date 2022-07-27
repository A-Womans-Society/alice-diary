package com.alice.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.alice.project.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAccount extends User {

	private static final long serialVersionUID = 1L;
	private Member member;

	public static List<SimpleGrantedAuthority> createAuthor() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (SimpleGrantedAuthority a : authorities) {
			authorities.add(new SimpleGrantedAuthority("USER_IN"));
		}
		return authorities;
	}

	public MemberAccount(Member member, List<SimpleGrantedAuthority> authorities) {
		super(member.getId(), member.getPassword(), authorities);
		this.member = member;
	}

}