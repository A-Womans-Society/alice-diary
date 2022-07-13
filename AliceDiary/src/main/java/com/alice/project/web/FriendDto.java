package com.alice.project.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// 친구 검색 했을 때 보이는 Dto
@Getter @Setter
@NoArgsConstructor
public class FriendDto {

	private Long adderNum; // 등록하는 친구 회원번호
	private Long addeeNum; // 등록되는 친구 회원번호
	private Long groupNum = 1L; // 기본그룹으로 설정
	
	public FriendDto(Long adderNum, Long addeeNum, Long groupNum) {
		super();
		this.adderNum = adderNum;
		this.addeeNum = addeeNum;
		this.groupNum = groupNum;
	}
	
	
}
