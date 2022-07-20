package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Reply;

@Repository
@Transactional(readOnly = true)
public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findByPostNum(Long num);

//	@Query(value="SELECT " 
//			+ "r.reply_num, r.parent_rep_num, r.content, r.rep_date, r.member_num, r.heart, r.edit "
//			+ "FROM reply r WHERE r.post_num= :postNum "
//			+ "GROUP BY r.reply_num, r.parent_rep_num, r.content, r.rep_date, r.member_num, r.heart, r.edit", nativeQuery=true)
//	List<Reply> findGroupByPostNum(Long postNum);

	@Modifying
	@Transactional
	@Query(value = "delete from Reply where post_num = :num", nativeQuery = true)
	Integer deletePostwithReply(Long num);

	@Query(value = "SELECT * FROM Reply WHERE post_num = :num AND parent_rep_num IS NULL ORDER BY rep_date DESC", nativeQuery = true)
	List<Reply> findParentReplysByNum(Long num);

	@Query(value = "SELECT * FROM Reply WHERE parent_rep_num = :num ORDER BY rep_date DESC", nativeQuery = true)
	List<Reply> findChildByParentNum(Long num);

}
