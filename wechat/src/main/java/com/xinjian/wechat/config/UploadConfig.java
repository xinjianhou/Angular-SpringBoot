package com.xinjian.wechat.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * config the path for uploading file
 * @author
 *
 */
@Data
@Component
@ConfigurationProperties(prefix="profile")
public class UploadConfig {
	
	private String uploadPath;

}
