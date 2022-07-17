package com.alice.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
	
	/* 모든 메시지 반환 */ 
	List<Message> findAll();

	List<Message> findMsgByLiveMessageToNum(Long num);
	
//	/* 보낸 회원 번호로 메시지 찾기 */
//	List<Message> findByMessageFromNum(Long messageFromNum);
	
//	/* 보낸 회원번호와 받은 회원번호로 메시지 찾기 */
//	List<Message> findByMessageFromNumAndMessageToNum(Long messageFromNum, Long messageToNum);
	
//	@Modifying
//	@Query("UPDATE Message m " +
//			    "SET m.senderStatus = 0 " +
//			    "WHERE m.messageFromNum= :messageFromNum AND messageToNum= :messageToNum")
//	int updateMsgRelationFrom(Long messageFromNum, Long messageToNum);
//	
//	@Modifying
//	@Query("UPDATE Message m " +
//		    "SET m.receiverStatus = 0 " +
//		    "WHERE m.messageFromNum= :messageToNum and messageToNum= :messageFromNum")
//	int updateMsgRelationTo(Long messageFromNum, Long messageToNum);	

	
}
