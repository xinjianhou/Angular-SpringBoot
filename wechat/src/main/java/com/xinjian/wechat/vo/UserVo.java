package com.xinjian.wechat.vo;

import com.xinjian.wechat.validation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @ValidEmail
    private String email;

    private Boolean enabled;
}
