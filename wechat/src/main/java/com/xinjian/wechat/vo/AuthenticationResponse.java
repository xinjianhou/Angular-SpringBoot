package com.xinjian.wechat.vo;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String username;

    public AuthenticationResponse(String token, String username){
        this.token = token;
        this.username = username;
    }
}
