package com.alice.project.web;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
// 사용자가 넣는 값들만 사용하는게 DTO
@Getter @Setter
public class FriendsDto {
	
	private Long memNum;
	private String memId;
	private String name;
	private String mobile;
	private LocalDate birth;
	private String gender;
	private String email;
	private String group;
	private String mbti;
	private String wishlist;
	private String profileImg;
	
}
