package com.xinjian.wechat.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class SearchVo {

    private FileVo file;

    @NotNull
    private String content;

}
