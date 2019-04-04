package com.xinjian.wechat.controller;

import com.xinjian.wechat.vo.SearchVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.base-path}/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    @GetMapping(value = "/{keyWord}")
    public SearchVo find(@PathVariable final String keyWord) {
        return new SearchVo();
    }
}
