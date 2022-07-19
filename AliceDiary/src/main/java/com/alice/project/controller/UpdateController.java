package com.alice.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.UpdateService;
import com.alice.project.service.ViewService;
import com.alice.project.service.WriteService;
import com.alice.project.web.WriteFormDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UpdateController {

	@Autowired
	private ViewService viewService;
	

	@Autowired
	private AttachedFileService attachedFileService;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private WriteService writeService;
	

	@GetMapping("/community/put")
	public String getUpdate(Long num, Model model, Pageable pageable) {
		log.info("수정컨트롤러 get");

		Post getUpdate = viewService.postView(num);

		WriteFormDto updateDto = new WriteFormDto(num, getUpdate.getTitle(), getUpdate.getContent());
		List<AttachedFile> files = attachedFileService.fileView(getUpdate, pageable);

		model.addAttribute("files", files);
		model.addAttribute("updateDto", updateDto);
		return "community/updateForm";
	}


	@PostMapping("/community/put")
	public String updatePorc(WriteFormDto updateDto, HttpSession session, @AuthenticationPrincipal UserDetails user) {

		updateService.updatePost(updateDto.getPostNum(), updateDto);
		
		Post updatedPost = writeService.findOne(updateDto.getPostNum());

		attachedFileService.postFileUpload(updateDto.getOriginName(), updatedPost, session, user.getUsername());

		return "redirect:list";
	}

	
}
