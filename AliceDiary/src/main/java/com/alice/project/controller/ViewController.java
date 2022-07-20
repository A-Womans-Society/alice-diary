package com.alice.project.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.ReplyService;
import com.alice.project.service.ViewService;
import com.alice.project.web.ReplyDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ViewController {

	@Autowired
	private ViewService viewService;

	@Autowired
	private AttachedFileService attachedFileService;

	@Autowired
	private ReplyService replyService;

	@GetMapping("community/get")
	public String postView(Model model, Long num, Pageable pageable, HttpSession session) {

		log.info("num :" + num);

		// model.addAttribute("memId", 1); // 세션대신 아이디를 하드코딩!!
		Post viewPost = viewService.postView(num);

		viewService.viewCntUp(num);

		model.addAttribute("postView", viewPost);

		List<AttachedFile> files = attachedFileService.fileView(viewPost, pageable);
		model.addAttribute("files", files);

		List<ReplyDto> replyList = replyService.replyList(num);
		
		for (ReplyDto rdto : replyList) {
			log.info("리스트 각각 : " + rdto.toString());
		}
		model.addAttribute("replyList", replyList);

		return "community/postView";
	}

	@GetMapping("community/download/{num}")
	public ResponseEntity<UrlResource> fileDownload(@PathVariable("num") Long num)
			throws MalformedURLException, UnsupportedEncodingException {

		return attachedFileService.postFileDownload(num);
	}

}
