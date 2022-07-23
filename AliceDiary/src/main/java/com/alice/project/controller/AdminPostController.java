package com.alice.project.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
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
import com.alice.project.domain.Reply;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.MemberService;
import com.alice.project.service.PostService;
import com.alice.project.service.ReplyService;
import com.alice.project.web.PostSearchDto;
import com.alice.project.web.ReplyDto;
import com.alice.project.web.WriteFormDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPostController {
	
	private final ReplyService replyService;
	private final PostService postService;
	private final AttachedFileService attachedFileService;
	private final MemberService memberService;
	
	// 공지사항 관리
	/* 공지사항 목록 */
	@GetMapping("/notice/list")
	public String showNoticeList(@PageableDefault(page=0, size=10, direction = Sort.Direction.DESC) Pageable pageable, 
			@ModelAttribute("postSearchDto") PostSearchDto postSearchDto,
			Model model, Long num) {
		Page<Post> notices = null;
		String type = postSearchDto.getType();
		String keyword = postSearchDto.getKeyword();
		
		if (keyword == null) {
			notices = postService.notceList(pageable);
		} else {
			notices = postService.searchNoticeList(postSearchDto, pageable); // 새로운 서비스의 메서드 사용할 예정
		}

		Long size = notices.getTotalElements();
		int nowPage = notices.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1);
		int endPage = 0;
		if (startPage == 1) {
			if (notices.getTotalPages() < 5) {
				endPage = notices.getTotalPages();
			} else {
				endPage = 5;
			}
		} else {
			endPage = Math.min(nowPage + 2, notices.getTotalPages());
		}

		if (endPage == notices.getTotalPages() && (endPage - startPage) < 5) {
			startPage = (endPage - 4 <= 0) ? 1 : endPage - 4;
		}
		
		model.addAttribute("notices", notices);
		model.addAttribute("nowPage",nowPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("size", size);		
		
		return "/admin/noticeList";
	}	
	
	/* 공지사항 상세보기 */
	@GetMapping("/notice/get")
	public String postView(Model model, Long num, Pageable pageable, HttpSession session) {

		log.info("num :" + num);
		Post viewPost = postService.postView(num);

		postService.viewCntUp(num);

		model.addAttribute("postView", viewPost);

		List<AttachedFile> files = attachedFileService.fileView(viewPost, pageable);
		model.addAttribute("files", files);

		List<ReplyDto> replyList = replyService.replyList(num);

		model.addAttribute("replyList", replyList);

		return "/admin/noticeView";
	}
	
	/* 공지사항 쓰기 폼 반환 */
	@GetMapping("/notice/post")
	public String writeform(Model model) {
		log.info("get");
		model.addAttribute("writeFormDto", new WriteFormDto());
		return "/admin/writeForm";
	}

	/* 공지사항 쓰기 */
	@PostMapping("/notice/post")
	public String writeSubmit(WriteFormDto writeFormDto, HttpSession session,
			@AuthenticationPrincipal UserDetails user) {
		log.info("controller 실행");

		Member member = memberService.findById(user.getUsername());

		Post post = Post.createNotice(writeFormDto, member);
		postService.write(post);

		attachedFileService.postFileUpload(writeFormDto.getOriginName(), postService.write(post), session,
				user.getUsername());

		return "redirect:/admin/notice/list";
	}
	
	/* 공지사항 수정 폼 받기*/
	@GetMapping("/notice/put")
	public String getUpdate(Long num, Model model, Pageable pageable) {
		log.info("수정컨트롤러 get");

		Post getUpdate = postService.postView(num);

		WriteFormDto updateDto = new WriteFormDto(num, getUpdate.getTitle(), getUpdate.getContent());
		List<AttachedFile> files = attachedFileService.fileView(getUpdate, pageable);

		model.addAttribute("files", files);
		model.addAttribute("updateDto", updateDto);

		return "/admin/updateForm";
	}

	/* 공지사항 수정 */
	@PostMapping("/notice/put")
	public String updatePorc(WriteFormDto updateDto, HttpSession session, @AuthenticationPrincipal UserDetails user) {

		postService.updatePost(updateDto.getPostNum(), updateDto);

		Post updatedPost = postService.findOne(updateDto.getPostNum());

		attachedFileService.postFileUpload(updateDto.getOriginName(), updatedPost, session, user.getUsername());

		return "redirect:/admin/notice/list";
	}

	/* 공지사항 수정하면서 파일 하나 삭제 */
	@PostMapping("/notice/put/filedelete")
	@ResponseBody
	public JSONObject oneFileDelete(Long num, Long postNum) {
		log.info("!!!!!!! file num : " + num);

		postService.deleteOneFile(num);

		JSONObject jObj = new JSONObject();

		List<AttachedFile> files = attachedFileService.newFileView(postNum);

		jObj.put("files", files);

		return jObj;
	}

	/* 공지사항 삭제 */
	@RequestMapping("/notice/delete")
	public String postDelete(Long num) {
		postService.deletePostwithReply(num);
		postService.deletePostwithFile(num);
		postService.deletePost(num);

		return "redirect:/admin/notice/list";
	}
	
	/* 공지사항 댓글 등록 */
    @PostMapping("/notice/reply")
    @ResponseBody
    public JSONObject replyWrite(String memberId, Long postNum, String content) {
		log.info(memberId, postNum, content);
		Reply newReply = replyService.replyWrite(memberId, postNum, content);
		
		JSONObject jObj = new JSONObject();
		
		jObj.put("replyNum", newReply.getNum());
		jObj.put("id", newReply.getMember().getId());
		jObj.put("repDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(newReply.getRepDate()));
		jObj.put("repContent", newReply.getContent());
		jObj.put("postNum", newReply.getPost().getNum());
	
		return jObj;
    }

 	/* 공지사항 대댓글 등록 */
	@PostMapping("/notice/replyreply")
	@ResponseBody
	public JSONObject replyReplyWrite(String memberId, Long postNum, Long parentRepNum, String content) {
	   log.info(memberId, postNum, parentRepNum, content);
	   Reply newReplyReply = replyService.replyReplyWrite(memberId, postNum, parentRepNum, content);
	
	   JSONObject jObj = new JSONObject();
	
	   jObj.put("replyNum", newReplyReply.getNum());
	   jObj.put("parentRepNum", newReplyReply.getParentRepNum());
	   jObj.put("id", newReplyReply.getMember().getId());
	   jObj.put("repDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(newReplyReply.getRepDate()));
	   jObj.put("repContent", newReplyReply.getContent());
	
	   return jObj;
	
	}

	/* 공지사항 댓글 삭제 */
	@PostMapping("/notice/deletereply")
	public String replyDelete(Long num) {
	   log.info("컨트롤러 실행 ");
	
	   replyService.replyDelete(num);
	
	   return "redirect:/admin/notice/list";
	}
	
	// 공개 게시판 관리
	/* 공개 게시판 목록 */
	@GetMapping("/open/list")
	public String showOpenCommunityList(@PageableDefault(page=0, size=10, direction = Sort.Direction.DESC) Pageable pageable, 
			@ModelAttribute("postSearchDto") PostSearchDto postSearchDto,
			Model model, Long num) {
		Page<Post> opens = null;
		String type = postSearchDto.getType();
		String keyword = postSearchDto.getKeyword();
		
		if (keyword == null) {
			opens = postService.openList(pageable);
		} else {
			opens = postService.searchList(postSearchDto, pageable); // 새로운 서비스의 메서드 사용할 예정
		}

		Long size = opens.getTotalElements();
		int nowPage = opens.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1);
		int endPage = 0;
		if (startPage == 1) {
			if (opens.getTotalPages() < 5) {
				endPage = opens.getTotalPages();
			} else {
				endPage = 5;
			}
		} else {
			endPage = Math.min(nowPage + 2, opens.getTotalPages());
		}

		if (endPage == opens.getTotalPages() && (endPage - startPage) < 5) {
			startPage = (endPage - 4 <= 0) ? 1 : endPage - 4;
		}
		
		model.addAttribute("list", opens);
		model.addAttribute("nowPage",nowPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("size", size);		
		
		return "/admin/openList";
	}	
	
	/* 공개 게시판 상세보기 */
	@GetMapping("/open/get")
	public String postView(Model model, Long num, Pageable pageable, HttpSession session,
			@AuthenticationPrincipal UserDetails user) {

		log.info("num :" + num);
		Post viewPost = postService.postView(num);

		postService.viewCntUp(num);

		model.addAttribute("postView", viewPost);

		List<AttachedFile> files = attachedFileService.fileView(viewPost, pageable);
		model.addAttribute("files", files);

		List<ReplyDto> replyList = replyService.replyList(num);

		model.addAttribute("replyList", replyList);
		model.addAttribute("member", memberService.findById(user.getUsername()));

		return "/admin/openView";
	}
	
}