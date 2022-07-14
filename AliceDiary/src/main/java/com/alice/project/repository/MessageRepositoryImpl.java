package com.alice.project.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Message;

@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

	@Autowired EntityManager entityManager;

//	@Override
//	public Message findRecentMsgByFromNum(Long messageFromNum) {
//		List<Message> resultList = entityManager.createQuery(
//				"SELECT m FROM Message as m WHERE 1=1 AND sendDate = (SELECT MAX(sendDate) FROM Message "
//				+ "WHERE messageFromNum=?1)", Message.class)
//				.setParameter(1, messageFromNum)
//				.getResultList();
//        return resultList.get(0);
//	}	
	
	@Override
	public Message findRecentMsgByNum(Long messageFromNum, Long messageToNum) {
		List<Message> resultList = entityManager.createQuery(
				"SELECT m FROM Message as m WHERE 1=1 AND sendDate = (SELECT MAX(sendDate) FROM Message "
				+ "WHERE messageFromNum=?1 and messageToNum=?2)", Message.class)
				.setParameter(1, messageFromNum)
                .setParameter(2, messageToNum)
				.getResultList();
        return resultList.get(0);
	}

	@Override
	public Message deleteMsgByNum(Long messageFromNum, Long messageToNum) {
		List<Message> resultList = entityManager.createQuery(
				"DELETE FROM Message AS m"
				+ "WHERE m.messageFromNum = :messageFromNum and m.messageToNum = :messageToNum)", Message.class)
				.setParameter(1, messageFromNum)
                .setParameter(2, messageToNum)
				.getResultList();
        return resultList.get(0);
	}

	@Override
	public List<Message> findByMessageFromNum(Long messageFromNum) {
		List<Message> resultList = entityManager.createQuery(
				"SELECT m FROM Message as m WHERE messageFromNum=?1", Message.class)
				.setParameter(1, messageFromNum)
				.getResultList();
        return resultList;
	}
	
	// 정상적인 메시지목록 반환
	public List<Message> findByLiveMessageFromNum(Long messageFromNum) {
		List<Message> resultList = entityManager.createQuery(
				"SELECT m FROM Message as m WHERE messageFromNum=?1 and senderStatus=1", Message.class)
				.setParameter(1, messageFromNum)
				.getResultList();
        return resultList;
	}
	
	// 쪽지함 하나 보여주기
	public List<Message> findMsgs(Long mfn, Long mtn) {
		List<Message> resultList = entityManager.createQuery(
				"SELECT m FROM Message as m "
				+ "WHERE messageFromNum=?1 and messageToNum=?2 ORDER BY sendDate DESC", Message.class)
				.setParameter(1, mfn)
                .setParameter(2, mtn)
				.getResultList();
        return resultList;
	}
	

	@Override
	public List<Message> findByMessageFromNumAndMessageToNum(Long messageFromNum, Long messageToNum) {
		List<Message> resultList = entityManager.createQuery(
				"SELECT m FROM Message as m WHERE messageFromNum=?1 and messageToNum=?2", Message.class)
				.setParameter(1, messageFromNum)
				.setParameter(2, messageToNum)
				.getResultList();
        return resultList;
	}

	@Override
	public Integer updateMsgRelationFrom(Long messageFromNum, Long messageToNum) {
		String sql = "UPDATE Message m " +
			    "SET senderStatus = 0 " +
			    "WHERE m.messageFromNum= :messageFromNum and messageToNum= :messageToNum";

		int fromResult = entityManager.createQuery(sql)
		        .setParameter("messageFromNum", messageFromNum)
		        .setParameter("messageToNum", messageToNum)
		        .executeUpdate();

        return fromResult;
		
	}

	@Override
	public Integer updateMsgRelationTo(Long messageFromNum, Long messageToNum) {
		String sql = "UPDATE Message m " +
			    "SET m.receiverStatus = 0 " +
			    "WHERE m.messageFromNum= :messageFromNum and messageToNum= :messageToNum";

		int toResult = entityManager.createQuery(sql)
		        .setParameter("messageFromNum", messageToNum)
		        .setParameter("messageToNum", messageFromNum)
		        .executeUpdate();
        return toResult;
	}

}
