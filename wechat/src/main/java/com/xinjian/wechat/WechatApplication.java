package com.xinjian.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.xinjian.wechat.repository"})
@EntityScan(basePackages = {"com.xinjian.wechat.domain"})
public class WechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}
}

