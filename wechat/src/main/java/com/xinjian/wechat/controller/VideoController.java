package com.xinjian.wechat.controller;

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

}
