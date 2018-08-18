package com.xinjian.wechat.vo;

import com.xinjian.wechat.validation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    @NotNull
    @Size(min = 4, max = 50)
    private String username;
    @NotNull
    @Size(min = 4, max = 100)
    private String password;
    @NotNull
    @ValidEmail
    @Size(min = 4, max = 50)
    private String email;

    private Boolean enabled;
}
