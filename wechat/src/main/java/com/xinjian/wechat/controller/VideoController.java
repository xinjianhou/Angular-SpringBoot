package com.xinjian.wechat.controller;

import com.xinjian.wechat.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="${api.base-path}/video", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoController {

    @Autowired
    VideoService videoService;
    @GetMapping(value="/home")
    public FileSystemResource videoSource() {

        return new FileSystemResource(videoService.getVideo("/Users/xinjianhou/Downloads/PMP（包含真题刷题升级版）/PMP第六版  视频课  光环 清辉/光环：2018年考前了解视频讲解/第1章PMBOK改版了/1.mp4"));


    }

}
