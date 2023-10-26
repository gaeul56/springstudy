package com.gdu.bbs.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.bbs.Util.MyPageUtils;
import com.gdu.bbs.dao.BbsMapper;
import com.gdu.bbs.dto.BbsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements BbsService {
	
	private final BbsMapper bbsMapper;
	private final MyPageUtils myPageUtils;

	@Override
	public void loadBbsList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		String strPage = opt.orElse("1");
		
		int page = Integer.parseInt(strPage);
		
		
		int total = bbsMapper.getBbsCount();
		
		int display = 10; // 한 화면에 표시될 갯수
		
		myPageUtils.setPaging(page, total, display);
		
		Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
										,"end", myPageUtils.getEnd());
		List<BbsDto> bbsList = bbsMapper.getBbsList(map);
		
		model.addAttribute("bbsList", bbsList);
		String contextPath = request.getContextPath();
		model.addAttribute("paging", myPageUtils.getMvcPaging(contextPath+"/bbs/list.do"));
		model.addAttribute("total", total);
		
		
	}

}
