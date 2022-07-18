package com.xinjian.wechat.controller;

import com.xinjian.wechat.service.SearchService;
import com.xinjian.wechat.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping(value = "/{keyWord}")
	public List<SearchVo> find(@PathVariable final String keyWord) {
		return searchService.doSearch(keyWord);
	}

}
