package com.xinjian.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinjian.wechat.domain.Knowledge;

/**
 * knowledge repository
 * @author
 *
 */

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

	//Knowledge(Entity Name)
	@Query("SELECT k FROM Knowledge k WHERE k.knowledgeName = :knowledgeName")
    Knowledge find(@Param("knowledgeName") String knowledgeName);

}
