package com.xinjian.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.xinjian.wechat.domain.Knowledge;

/**
 * knowledge repository
 * @author
 *
 */
@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

	//Knowledge(Entity Name)
	@Query("SELECT k FROM Knowledge k WHERE k.knowledgeName = :knowledgeName")
    Knowledge find(@Param("knowledgeName") String knowledgeName);
	
}
