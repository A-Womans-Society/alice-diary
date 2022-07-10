package com.alice.project.web;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FriendsForm {
	
	private Long memNum;
	private String memId;
	private String name;
	private String mobile;
	private LocalDateTime birth;
	private String gender;
	private String email;
	private String group;
	private String mbti;
	private String wishlist;
	private String profileImg;
}
