package com.alice.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	
	

}
