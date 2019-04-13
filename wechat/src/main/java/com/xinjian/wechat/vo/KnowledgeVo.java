package com.xinjian.wechat.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class KnowledgeVo implements Serializable {

	@NotNull
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 50)
	private String knowledgeName;
	
	@NotNull
	@Size(min = 0, max = 1000)
	private String knowledgeContent;
	
	private List<FileVo> fileVO = new ArrayList<>();
}
