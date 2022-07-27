package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.domain.Reply;
import com.alice.project.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	// 게시글 신고 유무 판단
	@Query(value = "SELECT r FROM Report r WHERE post_num = :post AND mem_num = :member AND report_type = 'POST'")
	List<Report> findPostReportExist(Post post, Member member);

	// 댓글 신고 유무 판단
	@Query(value = "SELECT r FROM Report r WHERE reply_num = :reply AND mem_num = :member AND report_type = 'REPLY'")
	List<Report> findReplyReportExist(Reply reply, Member member);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Report WHERE reply_num = :replyNum", nativeQuery = true)
	void deleteByReplyNum(Long replyNum);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Report WHERE post_num = :postNum", nativeQuery = true)
	void deleteByPostNum(Long postNum);
}
