package com.xinjian.wechat.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TodoVo implements Serializable {

    @NotNull
    private String id;

    private String desc;

    private Boolean completed;
}
