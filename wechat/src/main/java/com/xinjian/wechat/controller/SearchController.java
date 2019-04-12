package com.xinjian.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinjian.wechat.service.SearchServiceImpl;
import com.xinjian.wechat.vo.SearchVo;

/**
 * Search Controller
 * @author 
 *
 */
@RestController
@RequestMapping(value = "${api.base-path}/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

	@Autowired
	private SearchServiceImpl searchService;
	
	/**
	 * 
	 * @param keyWord
	 * @return
	 */
    @GetMapping(value = "/{keyWord}")
    public List<SearchVo> search(@PathVariable final String keyWord) {
    	
    	List<SearchVo> searchVos = new ArrayList<SearchVo>();
    	searchVos = searchService.search(keyWord);
    	
    	return searchVos;
    }
    
}
