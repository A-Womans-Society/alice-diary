package com.alice.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.UpdateService;
import com.alice.project.service.ViewService;
import com.alice.project.service.WriteService;
import com.alice.project.web.WriteFormDto;

@Controller
@RequestMapping("/AliceDiary")
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
		System.out.println("수정컨트롤러 get");

		Post getUpdate = viewService.postView(num);

//		model.addAttribute("getUpdate", getUpdate);
		WriteFormDto updateDto = new WriteFormDto(num, getUpdate.getTitle(), getUpdate.getContent());
		List<AttachedFile> files = attachedFileService.fileView(getUpdate, pageable);

		model.addAttribute("files", files);
		model.addAttribute("updateDto", updateDto);
		return "community/updateForm";
	}

	@PostMapping("/community/put")
	public String updatePorc(WriteFormDto updateDto, HttpSession session) {

		updateService.updatePost(updateDto.getNum(), updateDto);
		
		Post updatedPost = writeService.findOne(updateDto.getNum());

		attachedFileService.postFileUpload(updateDto.getOriginName(), updatedPost, session);

		return "redirect:list";
	}

	/*
	 * @PostMapping("community/put") public String updatePorc(Long num,
	 * UpdateFormDto updateFormDto, HttpSession session) {
	 * System.out.println("수정 컨트롤러 post작동/ num:" + num);
	 * updateService.updatePost(num, updateFormDto);
	 * 
	 * Post post = Post.updatePost(updateFormDto);
	 * 
	 * System.out.println(updateFormDto.getOriginName());
	 * attachedFileService.postFileUpload(updateFormDto.getOriginName(),
	 * writeService.write(post), session);
	 * 
	 * return "redirect:list"; }
	 */
	
}
