package com.alice.project.web;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alice.project.domain.PostType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteFormDto {

	private String title;
	
	private String content;
		
	private PostType postType;
	
	private List<MultipartFile> originName;
	
	
}
