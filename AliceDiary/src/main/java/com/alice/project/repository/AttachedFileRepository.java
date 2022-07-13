package com.alice.project.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.AttachedFile;

@Repository
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
	
	List<AttachedFile> findAllByPostNum(Long post, Pageable pageable);

}
