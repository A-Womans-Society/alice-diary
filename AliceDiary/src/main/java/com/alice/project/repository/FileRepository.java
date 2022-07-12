package com.alice.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.AttachedFile;

@Repository
public interface FileRepository extends JpaRepository<AttachedFile, Long> {
	
	

}
