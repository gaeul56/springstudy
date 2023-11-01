package com.gdu.myhome.service;

import java.io.File;
import java.util.List;

import org.springframework.remoting.rmi.JndiRmiClientInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.dto.UploadDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {
	private final UploadMapper uploadMapper;
	private final MyFileUtils  myFileUtils;
	private final MyPageUtils  myPageUtils;
	
	@Override
	public int addUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
		
		String title = multipartRequest.getParameter("title");
		String contents = multipartRequest.getParameter("contents");
		int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
		
		
		UploadDto upload = UploadDto.builder()
								.title(title)
								.contents(contents)
								.userDto(UserDto.builder()
										.userNo(userNo)
										.build())
								.build();
	//	int addUploadResult = uploadMapper.insertUpload(upload);
		
		List<MultipartFile> files = multipartRequest.getFiles("files");
		
		for(MultipartFile multipartFile : files) {
			if(multipartFile != null && !multipartFile.isEmpty()) {
				String path = myFileUtils.getUploadPath();
				File dir = new File(path);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				
				String originalFilename = multipartFile.getOriginalFilename();
				String filesystemName = myFileUtils.getFilesystemName(originalFilename);
				File file = new File(dir, filesystemName);
				
				multipartFile.transferTo(file);
				
				
		
				
			}
		}
		
		return 0;
	}
	
}
