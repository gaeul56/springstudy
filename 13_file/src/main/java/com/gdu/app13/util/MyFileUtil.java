package com.gdu.app13.util;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {

	// 파일이 저장될 경로 반환하기
	public String getPath() {
		
	  //현재 날짜를 얻어옴
	  /* /storage/yyyy/MM/dd */
	  LocalDate today = LocalDate.now();
	  
	  //파일이 저장될 경로를 문자열로 반환
	  return "/Users/seoga-eul/storage/" + 
			  today.getYear() + "/" + 
			  String.format("%02d",today.getMonthValue())+ "/" +
			  String.format("%02d", today.getDayOfMonth());
	 //return "/storage/"+ DateTimeFormatter.ofPattern("yyy/MM/dd").format(today);
	}
	
	//파일이 저장될 이름 반환하기
	public String getFilesystemName(String originalName) {
		/* UUID.확장자 */
		
		//파일 확장자 추출을 위한 변수 초기화
		String extName = null;
		
		//만약 파일 이름이 "tar.gz"로 끝난다면 예외 처리
		if(originalName.endsWith("tar.gz")) { //확장자에 마침표가 포함되는 예외경우를 처리한다.
			extName = "tar.gz";
		} else {
			//파일 이름에서 마지막 마침표를 기준으로 확장자를 추출합니다.
			String[] arr = originalName.split("\\."); // [.] 또는 \\.
			extName = arr[arr.length-1];
		}
		//UUID를 사용하여 고유한 파일 이름을 생성하고, 확장자를 추가하여 반환합니다.
		return UUID.randomUUID().toString().replace("-", "")  + "." + extName;
		
	}
}
