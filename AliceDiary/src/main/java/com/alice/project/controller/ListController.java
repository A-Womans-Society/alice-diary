package com.alice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.project.domain.Post;
import com.alice.project.service.ListService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ListController {

	@Autowired
	private ListService listService;

	@GetMapping("/community/list")
	public String list(Model model,
			@PageableDefault(page=0, size=5, sort="num", direction=Sort.Direction.DESC)Pageable pageable, Long num) {
		
		Page<Post> list = listService.list(pageable);
		
		log.info("service run");
		
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1);
		int endPage = Math.min(nowPage + 3, list.getTotalPages());
		
		
		model.addAttribute("list",list);
		model.addAttribute("nowPage",nowPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		
		log.info("nowPage:" + nowPage);
		log.info("startPage:" + startPage);
		log.info("endPage:" + endPage);
		
		return "community/list";
	}
}
