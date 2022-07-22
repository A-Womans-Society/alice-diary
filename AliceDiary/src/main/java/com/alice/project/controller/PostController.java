package com.alice.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.MemberService;
import com.alice.project.service.PostService;
import com.alice.project.service.ReplyService;
import com.alice.project.web.PostSearchDto;
import com.alice.project.web.ReplyDto;
import com.alice.project.web.WriteFormDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private AttachedFileService attachedFileService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReplyService replyService;

	// 글쓰기
	@GetMapping("/community/post")
	public String writeform(Model model) {
		log.info("get");
		model.addAttribute("writeFormDto", new WriteFormDto());
		return "community/writeForm";
	}

	// 글쓰기
	@PostMapping("/community/post")
	public String writeSubmit(WriteFormDto writeFormDto, HttpSession session,
			@AuthenticationPrincipal UserDetails user) {
		log.info("controller 실행");

		Member member = memberService.findById(user.getUsername());

		Post post = Post.createPost(writeFormDto, member);
		postService.write(post);

		attachedFileService.postFileUpload(writeFormDto.getOriginName(), postService.write(post), session,
				user.getUsername());

		return "redirect:list";
	}

	// 게시글 리스트 가져오기
	@GetMapping("/community/list")
	public String list(Model model, @ModelAttribute("postSearchDto") PostSearchDto postSearchDto,
			@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable) {

		log.info("컨트롤러 로그 postSearchDto :" + postSearchDto.toString());

		String keyword = postSearchDto.getKeyword();
		Long size = 0L;
		Page<Post> list = null;

		if (keyword == null) {
			list = postService.list(pageable);
			size = list.getTotalElements();
		} else {
			list = postService.searchList(postSearchDto, pageable);
			size = list.getTotalElements();
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1);
		int endPage = 0;
		if (startPage == 1) {
			if (list.getTotalPages() < 5) {
				endPage = list.getTotalPages();
			} else {
				endPage = 5;
			}
		} else {
			endPage = Math.min(nowPage + 2, list.getTotalPages());
		}

		if (endPage == list.getTotalPages() && (endPage - startPage) < 5) {
			startPage = (endPage - 4 <= 0) ? 1 : endPage - 4;
		}

		model.addAttribute("list", list);
		model.addAttribute("size", size);
		log.info("size : " + size);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", postSearchDto.getType());

		log.info("nowPage:" + nowPage);
		log.info("startPage:" + startPage);
		log.info("endPage:" + endPage);

		return "community/list";
	}

	// 게시글 상세보기
	@GetMapping("/community/get")
	public String postView(Model model, Long num, Pageable pageable, HttpSession session) {

		log.info("num :" + num);
		Post viewPost = postService.postView(num);

		postService.viewCntUp(num);

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

	// 게시글 수정하기 첨부파일도 수정
	@GetMapping("/community/put")
	public String getUpdate(Long num, Model model, Pageable pageable) {
		log.info("수정컨트롤러 get");

		Post getUpdate = postService.postView(num);

		WriteFormDto updateDto = new WriteFormDto(num, getUpdate.getTitle(), getUpdate.getContent());
		List<AttachedFile> files = attachedFileService.fileView(getUpdate, pageable);

		model.addAttribute("files", files);
		model.addAttribute("updateDto", updateDto);

		return "community/updateForm";
	}

	// 게시글 수정하기 첨부파일도 수정
	@PostMapping("/community/put")
	public String updatePorc(WriteFormDto updateDto, HttpSession session, @AuthenticationPrincipal UserDetails user) {

		postService.updatePost(updateDto.getPostNum(), updateDto);

		Post updatedPost = postService.findOne(updateDto.getPostNum());

		attachedFileService.postFileUpload(updateDto.getOriginName(), updatedPost, session, user.getUsername());

		return "redirect:list";
	}

	// 게시글 수정에서 파일하나 삭제하기
	@PostMapping("/community/put/filedelete")
	@ResponseBody
	public JSONObject oneFileDelete(Long num, Long postNum) {
		log.info("!!!!!!! file num : " + num);

		postService.deleteOneFile(num);

		JSONObject jObj = new JSONObject();

		List<AttachedFile> files = attachedFileService.newFileView(postNum);

		jObj.put("files", files);

		return jObj;
	}

	// 게시글 삭제하기
	@RequestMapping("/community/delete")
	public String postDelete(Long num) {
		log.info("컨트롤러 실행 num:" + num);

		postService.deletePostwithReply(num);
		postService.deletePostwithFile(num);
		postService.deletePost(num);

		return "redirect:list";
	}

}
