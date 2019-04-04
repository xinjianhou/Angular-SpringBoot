package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.User;
import com.xinjian.wechat.service.UserDetailsServiceImpl;
import com.xinjian.wechat.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "${api.base-path}/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"User Controller"})
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/{username}")
    @ApiOperation(value = "Get user by username")
    User getUserByName(@PathVariable String username) {
        User user = userService.checkUserByUsername(username);
        return user;
    }
    @Valid
    @PostMapping()
    @ApiOperation(value = "create a user")
    UserVo createUser(@RequestBody UserVo user){
       return  userService.createUser(user);

    }

}
