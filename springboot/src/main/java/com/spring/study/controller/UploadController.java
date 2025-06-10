package com.spring.study.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.study.dto.UploadResultDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UploadController {

	@Value("${com.spring.upload.path}")
	private String uploadPath;
	
	@PostMapping("/uploadAjax")
	public ResponseEntity<List<UploadResultDTO>> uploadFile(@RequestPart("uploadFiles") MultipartFile[] uploadFiles) {
		List<UploadResultDTO> resultDtoList = new ArrayList<>();
		
		for (MultipartFile uploadFile : uploadFiles) {
			
//			이미지가 아닌 경우
			if (uploadFile.getContentType().startsWith("image") == false) {
				log.warn("이미지 파일이 아닙니다.");
				
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
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
				
//				썸네일 생성
				String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
				log.info("썸네일 세이브 네임...." + thumbnailSaveName);
				File thumbnailFile = new File(thumbnailSaveName);
				
				Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
				
				resultDtoList.add(new UploadResultDTO(fileName, uuid, folderPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<>(resultDtoList, HttpStatus.OK);
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
	
//	이미지 표시
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName,
										  @RequestParam("size") String size) {
		ResponseEntity<byte[]> result = null;
		
		try {
			log.info("요청 fileName (원본): {}", fileName);
			String srcFileName = URLDecoder.decode(fileName, "UTF-8");
			log.info("srcFileName : {}", srcFileName);
			log.info("디코딩된 srcFileName: {}", srcFileName);
			
			File file = new File(uploadPath + File.separator + srcFileName);
			log.info("실제 파일 경로: {}", file.getAbsolutePath());
	        log.info("파일 존재 여부: {}", file.exists());
	        
	        if (size != null && size.equals("1")) {
	        	file = new File(file.getParent(), file.getName().substring(2));
	        }
	        
	        String contentType = Files.probeContentType(file.toPath());
	        log.info("Content-Type: {}", contentType);
			
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
			log.info("== 디버깅: srcFileName = " + srcFileName);
			log.info("== 디버깅: file.getAbsolutePath() = " + file.getAbsolutePath());
			log.info("== 디버깅: file.exists() = " + file.exists());
		} catch (Exception e) {
			 log.error("파일 출력 에러", e); // 전체 에러/스택트레이스 출력
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("/removeFile")
	public ResponseEntity<Boolean> removeFile(@RequestParam("fileName") String fileName) {
		String srcFileName = null;
		
		try {
			srcFileName = URLDecoder.decode(fileName, "UTF-8");
			File file = new File(uploadPath + File.separator + srcFileName);
			
			boolean result = file.delete();
			
			File thumbnail = new File(file.getParent(), "s_" + file.getName());
			
			log.info("썸네일..... " + thumbnail.getPath());
			result = thumbnail.delete();
			
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

