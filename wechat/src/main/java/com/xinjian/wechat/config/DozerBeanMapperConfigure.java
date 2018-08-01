package com.xinjian.wechat.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DozerBeanMapperConfigure {

    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("mapping/dozerMapping.xml"));
        return mapper;
    }

}
