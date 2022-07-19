package com.alice.project.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Post;
import com.alice.project.repository.AttachedFileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttachedFileService {

	@Autowired
	private AttachedFileRepository attachedFileRepository;
	
	

	public void postFileUpload(List<MultipartFile> files, Post post, HttpSession session, String id) {
		log.info("list size : " + files.size());
		if (files.size() != 0) {
			log.info("service run");
			for (MultipartFile multipartFile : files) {

				String savePath = "C:\\Temp\\upload\\";
				String ofile = multipartFile.getOriginalFilename();
				String sfile = postSaveFile(multipartFile, savePath, session, id);

				log.info("service run222222222");

				AttachedFile file = new AttachedFile(ofile, sfile, savePath, post);

				attachedFileRepository.save(file);
				log.info("service run444444444");

			}

		} else {
			log.info("upload file FAIL!!");
		}
	}

	public String postSaveFile(MultipartFile file, String savePath, HttpSession session, String id) {

		String ofile = file.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String sfile = id+"_" +currentTime + "_" + ofile;

		log.info("ofile:" + ofile);
		log.info("sfile:" + sfile);
		log.info("savePath:" + savePath);

		try {
			file.transferTo(new File(savePath + sfile));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return sfile;
	}
	
	public ResponseEntity<UrlResource> postFileDownload(Long num) throws MalformedURLException, UnsupportedEncodingException{
		
		Optional<AttachedFile> findFile = attachedFileRepository.findById(num);
		AttachedFile attachedFile = findFile.orElse (null);
		if (findFile == null) return null;
		
		String sFileName = attachedFile.getSaveName();
		String oFileName = attachedFile.getOriginName();
		
		
		String encodeoFileName;

			encodeoFileName = URLEncoder.encode(oFileName,"UTF-8").replace("+", "%20");
			
			String savedFilePath = "attachment; filename=\"" + encodeoFileName + "\"";
			UrlResource resource = new UrlResource("file:" + attachedFile.getFilePath() + sFileName);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, savedFilePath)
					.body(resource);
			
	}
	
	public List<AttachedFile> fileView(Post post,Pageable pageable) {
		log.info("service run fileVIEW");
		List<AttachedFile> afs = attachedFileRepository.findAllByPostNum(post.getNum(),pageable);
		log.info("!!!!!!!!!!!!!!!!!!!!!!asf.size:" + afs.size());
		for (AttachedFile af : afs) {
			String oriName = af.getOriginName();
			log.info("AFS의 fileView!!! af : " + oriName);
		}
		return attachedFileRepository.findAllByPostNum(post.getNum(),pageable);
	}
	
	public List<AttachedFile> newFileView(Long postNum) {
		log.info("service run newfileVIEW");
		
		List<AttachedFile> afs = attachedFileRepository.findAllByPostNum(postNum);
		
		log.info("!!!!!!!!!!!!!!!!!!!!!!asf.size:" + afs.size());
		
		for (AttachedFile af : afs) {
			String oriName = af.getOriginName();
			String saveName = af.getSaveName();
			log.info("AFS의 oriName!!! af : " + oriName);
			log.info("AFS의 saveName!!! af : " + saveName);
		}
		return afs;
	}
	

	
	
}
