package com.alice.project.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alice.project.repository.FileRepository;

@Service
public class FileUploadService {

	@Autowired
	private FileRepository fileRepository;

	public String PostSaveFile(MultipartFile file, String savePath, HttpSession session) {
		
		String ofile = file.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		Long memberNum = (Long) session.getAttribute("member_num");
		String sfile = memberNum+ "_" + currentTime + "_" + ofile;

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


	public void postFileUpload(List<MultipartFile> files, HttpSession session) {
		System.out.println("list size : " + files.size());
		if (files.size() != 0) {
			System.out.println("service run");
			for (MultipartFile multipartFile : files) {
				
				String savePath = "C:\\Temp\\upload\\";
				String ofile = multipartFile.getOriginalFilename();
				String sfile = PostSaveFile(multipartFile,savePath, session);
				
				System.out.println("service run222222222");

				File file =file.builder(ofi)
				regcom.setImage(ofile);
				regcom.setImage(ofile);
				regcom.setImage(ofile);
				
				fileRepository.save(file);
				System.out.println("service run444444444");

			}

		} else {
			System.out.println("no upload file");
		}
	}
}
