package com.xinjian.wechat.controller;

import com.xinjian.wechat.vo.AuthenticationResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;


@RestController
@RequestMapping(value="/video")
public class VideoController {

    @GetMapping(value="/home")
    public FileSystemResource videoSource() {


        return new FileSystemResource(new File("/Users/xinjianhou/Downloads/1.mp4"));
    }

    @PostMapping(value="searchVideo")
    public AuthenticationResponse searchVideo(@RequestBody String name) {
        AuthenticationResponse ath =  new AuthenticationResponse("http://localhost:8888/auth/home","hh");

        return ath;
    }
}
