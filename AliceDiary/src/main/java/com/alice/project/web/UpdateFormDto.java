package com.alice.project.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormDto {

	private String title;
	
	private String content;
	
	private LocalDateTime updateDate;
	
	private List<MultipartFile> originName;
	
	
}
