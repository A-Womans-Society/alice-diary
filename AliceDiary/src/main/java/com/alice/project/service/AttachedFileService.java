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

@Service
public class AttachedFileService {

	@Autowired
	private AttachedFileRepository attachedFileRepository;

	public void postFileUpload(List<MultipartFile> files, Post post, HttpSession session) {
		System.out.println("list size : " + files.size());
		if (files.size() != 0) {
			System.out.println("service run");
			for (MultipartFile multipartFile : files) {

				String savePath = "C:\\Temp\\upload\\";
				String ofile = multipartFile.getOriginalFilename();
				String sfile = PostSaveFile(multipartFile, savePath, session);

				System.out.println("service run222222222");

//				AttachedFile file = new AttachedFile(ofile, sfile, savePath);
				AttachedFile file = new AttachedFile(ofile, sfile, savePath, post);

				attachedFileRepository.save(file);
				System.out.println("service run444444444");

			}

		} else {
			System.out.println("no upload file");
		}
	}

	public String PostSaveFile(MultipartFile file, String savePath, HttpSession session) {

		String ofile = file.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// String sfile = 사용자 아이디+ "_" + currentTime + "_" + ofile;

		String sfile = currentTime + "_" + ofile;

		System.out.println("ofile:" + ofile);
		System.out.println("sfile:" + sfile);
		System.out.println("savePath:" + savePath);

		try {
			file.transferTo(new File(savePath + sfile));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return sfile;
	}
	
	public ResponseEntity<UrlResource> postFileDownload(Long num) throws MalformedURLException, UnsupportedEncodingException{
		
		//엔티티에서 필드 값과 동일한 pk 정보를 가지고 와서 검증하는 단계
		Optional<AttachedFile> findFile = attachedFileRepository.findById(num);
		AttachedFile attachedFile = findFile.orElse (null);
		if (findFile == null) return null;
		
		String sFileName = attachedFile.getSaveName();
		String oFileName = attachedFile.getOriginName();
		
		//한글 인코딩
		String encodeoFileName;

			encodeoFileName = URLEncoder.encode(oFileName,"UTF-8").replace("+", "%20");
			
			String savedFilePath = "attachment; filename=\"" + encodeoFileName + "\"";
			UrlResource resource = new UrlResource("file:" + attachedFile.getFilePath() + sFileName);
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, savedFilePath)
					.body(resource);
			
	}
	
	public List<AttachedFile> fileView(Post post,Pageable pageable) {
		System.out.println("service run file");
		List<AttachedFile> afs = attachedFileRepository.findAllByPostNum(post.getNum(),pageable);
		System.out.println("asf.size:" + afs.size());
		for (AttachedFile af : afs) {
			String oriName = af.getOriginName();
			System.out.println("AFS의 fileView!!! af : " + oriName);
		}
		return attachedFileRepository.findAllByPostNum(post.getNum(),pageable);
	}
	
	public void deleteFile(Long num) {
		System.out.println("num:"+num);
		AttachedFile deleteFile = attachedFileRepository.findByNum(num);
		
		attachedFileRepository.delete(deleteFile);
			
	}
	
	public void deletePostwithFile(Long num) {
		System.out.println("num:"+num);
		AttachedFile deleteFile = attachedFileRepository.findByPostNum(num);
		
		attachedFileRepository.delete(deleteFile);
			
	}
	
	
	
	
	
}
