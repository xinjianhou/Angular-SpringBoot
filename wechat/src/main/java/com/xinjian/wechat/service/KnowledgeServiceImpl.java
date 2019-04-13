package com.xinjian.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinjian.wechat.domain.Knowledge;
import com.xinjian.wechat.repository.KnowledgeRepository;

/**
 * Knowledge Service
 * @author
 *
 */
@Service
public class KnowledgeServiceImpl {

	@Autowired
    private KnowledgeRepository knowledgeRepository;
	
	public Knowledge find(String knowledgeName){
		return knowledgeRepository.find(knowledgeName);
	}
	
	public boolean saveKnowledge(Knowledge knowledge){
    	return (null != knowledgeRepository.save(knowledge))? true :false;
   }
}
