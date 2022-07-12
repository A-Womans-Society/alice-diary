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

@Controller
@RequestMapping("/AliceDiary")
public class ListController {

	@Autowired
	private ListService listService;

	@GetMapping("/community/list")
	public String list(Model model, 
			@PageableDefault(page=0, size=5, sort="num", direction=Sort.Direction.DESC)Pageable pageable
			) {
		//page = 현재페이지, size=보여줄 게시물수, sort=페이징 조건, direction=오름정렬 
		Page<Post> list = null;
		list = listService.list(pageable);
		/*
		 * if(keyword != null) { list = listService.list(pageable); } else { list =
		 * listService.searchList(keyword, pageable); }
		 */
		System.out.println("service run");
		
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		model.addAttribute("list",list);
		model.addAttribute("nowPage",nowPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		
		return "community/list";
	}
}
