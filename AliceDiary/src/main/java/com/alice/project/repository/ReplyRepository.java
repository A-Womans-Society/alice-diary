package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alice.project.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

}
