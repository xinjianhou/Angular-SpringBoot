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

        return new FileSystemResource(videoService.getVideo("/Users/xinjianhou/music/iTunes/iTunes Media/Home Videos/sexo-18og-anjelica-121215.mp41"));


    }

}
