package com.xinjian.wechat.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinjian.wechat.domain.Knowledge;
import com.xinjian.wechat.service.KnowledgeServiceImpl;

/**
 * knowledge controller
 * @author
 */
@RestController
@RequestMapping(value = "${api.base-path}/knowledge", produces = MediaType.APPLICATION_JSON_VALUE)
public class KnowledgeController {

	@Autowired
	private KnowledgeServiceImpl knowledgeService;
	
    @PostMapping(value = "/")
    public Knowledge find(@RequestBody Knowledge knowledge) {
    	Knowledge kl = null;
    	if(StringUtils.isNotBlank(knowledge.getKnowledgeName())){
    		kl = knowledgeService.find(knowledge.getKnowledgeName());
    	}
    	return kl;
    }
}
