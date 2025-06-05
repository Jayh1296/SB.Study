package com.spring.study.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UploadController {

	@Value("${come.spring.upload.path}")
	private String uploadPath;
	
	@PostMapping("/uploadAjax")
	public void uploadFile(@RequestPart("uploadFiles") MultipartFile[] uploadFiles) {
		for (MultipartFile uploadFile : uploadFiles) {
			
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
			
			log.info("fileName : {}", fileName);
			
			String folderPath = makeFolder();
			
			String uuid = UUID.randomUUID().toString();
			
//			운영체제에 맞춰서 경로를 지정해줌 \\ // 와 같은 경로 패스가 다름
			String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
			
			Path savePath = Paths.get(saveName);
			
			try {
				uploadFile.transferTo(savePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	파일 저장 경로(디렉토리) 생성
	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		
		String folderPath = str.replace("//", File.separator);
		
		File uploadPathFolder = new File(uploadPath, folderPath);
		
		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
		}
		
		return folderPath;
	}
}
