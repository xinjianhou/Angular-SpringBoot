package com.xinjian.wechat.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FileVo {

    private Long id;

    private String fileName;

    @NotNull
    private Long fileSize;

    @NotNull
    private String location;

    private Date uploadDate;
}
