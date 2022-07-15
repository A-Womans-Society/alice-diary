package com.alice.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Table(name = "attachedFile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class AttachedFile {
	
	@Id @GeneratedValue
	@Column(name="file_num")
	private Long num; // 파일 번호
	private String originName; // 원본파일명 
	private String saveName; // 저장파일명
	private String filePath; // 파일경로
	
	private String postNum; // 소속 게시물번호
	private String messageNum; // 소속 쪽지번호
	
//	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
//	@JoinColumn(name="post_num")
//	private Post post; // 소속 게시물 객체
	
//	@ManyToOne(fetch=FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
//	@JoinColumn(name="message_num")
//	private Post message; // 소속 게시물 객체
	
	// 연관관계 메서드 (양방향관계)
//	public void setPost(Post post) {
//		this.post = post;
//		post.getFiles().add(this);
//	}
	
	@Builder
	public AttachedFile(String originName, String saveName, String filePath) {
		this.originName = originName;
		this.saveName = saveName;
		this.filePath = filePath;
	}

}
