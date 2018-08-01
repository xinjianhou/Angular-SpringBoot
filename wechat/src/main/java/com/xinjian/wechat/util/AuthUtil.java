package com.xinjian.wechat.util;

import com.xinjian.wechat.domain.Authority;

import java.util.ArrayList;
import java.util.List;

public class AuthUtil {
    public static List<Authority> setAuthority(){
        List<Authority> authorities= new ArrayList<>();
        Authority athu = new Authority();
        athu.setId(2L);
        athu.setName(AuthorityName.ROLE_ADMIN);
        authorities.add(athu);
        return authorities;
    }
}
