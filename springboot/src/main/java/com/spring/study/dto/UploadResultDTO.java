package com.spring.study.dto;

import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResultDTO {

	private String fileName;
	private String uuid;
	private String folderPath;
	
	public String getImageURL() {
		try {
			return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String getThumbnailURL() {
		try {
			return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
