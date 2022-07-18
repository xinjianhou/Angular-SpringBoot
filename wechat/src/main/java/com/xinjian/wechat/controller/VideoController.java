package com.xinjian.wechat.controller;

import com.xinjian.wechat.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="video", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoController {

    @Autowired
    VideoService videoService;
    @GetMapping(value="/home")
    public FileSystemResource videoSource() {

        return new FileSystemResource(videoService.getVideo("/Users/xinjianhou/Downloads/1.mp4"));


    }

}
