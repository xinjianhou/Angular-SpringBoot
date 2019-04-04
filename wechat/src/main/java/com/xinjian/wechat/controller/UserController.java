package com.freshman.controller;

import com.freshman.domain.User;
import com.freshman.service.UserDetailsServiceImpl;
import com.freshman.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "${api.base-path}/user")
@Api(tags = {"User Controller"})
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("getUserByUsername/{username}")
    @ApiOperation(value = "Get user by username")
    User getUserByName(@PathVariable String username) {
        User user = userService.checkUserByUsername(username);
        return user;
    }
    @Valid
    @PostMapping(value = "/createUser")
    @ApiOperation(value = "create a user")
    UserVo createUser(@RequestBody UserVo user){
       return  userService.createUser(user);

    }

}
