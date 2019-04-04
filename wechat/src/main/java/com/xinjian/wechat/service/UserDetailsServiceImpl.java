package com.xinjian.wechat.service;

import com.xinjian.wechat.domain.Authority;
import com.xinjian.wechat.domain.User;
import com.xinjian.wechat.repository.UserRepository;
import com.xinjian.wechat.util.AuthUtil;
import com.xinjian.wechat.vo.UserVo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return create(user);
    }

    private static org.springframework.security.core.userdetails.User create(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getAuthorities()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }

    public User checkUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserVo createUser(UserVo user) {

        User userBo = mapper.map(user, User.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userBo.setPassword(encoder.encode(userBo.getPassword()));
        userBo.setAuthorities(AuthUtil.setAuthority());
        userBo.setEnabled(false);
        userBo.setLastPasswordResetDate(new Date());
        User returnUser = userRepository.save(userBo);
        if (null != returnUser) {
            return user;
        } else return null;
    }


}
