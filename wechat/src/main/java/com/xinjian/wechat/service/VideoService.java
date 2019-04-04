package com.xinjian.wechat.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VideoService {

    public File getVideo(String videoName){
        return new File(videoName);
    }
}
