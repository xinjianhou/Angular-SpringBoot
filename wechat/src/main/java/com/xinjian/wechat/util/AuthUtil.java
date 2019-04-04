package com.freshman.util;

import com.freshman.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthUtil {
    public static List<Authority> setAuthority(){
        List<Authority> authorities= new ArrayList<>();
        Authority athu = new Authority();
        athu.setId(2L);
        athu.setName(AuthorityName.ROLE_ADMIN);
        authorities.add(athu);
        return authorities;
    }
    public static List<GrantedAuthority> createGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name())).collect(Collectors.toList());
    }

    public static List<GrantedAuthority> createGrantedAuthorities(String... authorities) {
        return Stream.of(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public static String[] getAuthorities(UserDetails user) {
        return user.getAuthorities().stream().map(GrantedAuthority::<String>getAuthority).toArray(String[]::new);
    }
}
