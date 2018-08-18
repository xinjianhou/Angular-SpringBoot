package com.xinjian.wechat.controller;

import com.xinjian.wechat.service.FileService;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static sun.misc.Version.print;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileService fileService;

//    @Mapping(value = "/download")
//    public FileSystemResource downloadFile(@RequestBody long id) {
//
//
//    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

         fileService.saveFile(file);
         return "ok";

    }


}
