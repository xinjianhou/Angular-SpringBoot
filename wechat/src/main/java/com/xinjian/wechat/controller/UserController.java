package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.User;
import com.xinjian.wechat.service.UserDetailsServiceImpl;
import com.xinjian.wechat.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("getUserByUsername/{username}")
    User getUserByName(@PathVariable String username) {
        User user = userService.checkUserByUsername(username);
        return user;
    }
    @Valid
    @PostMapping(value = "/createUser")
    UserVo createUser(@RequestBody UserVo user){
       return  userService.createUser(user);

    }

}
