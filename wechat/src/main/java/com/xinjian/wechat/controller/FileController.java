package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.File;
import com.xinjian.wechat.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "${api.base-path}/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/download")
    public FileSystemResource downloadFile(@RequestBody long id) {
    File file = fileService.getFileById(id);
    return new FileSystemResource(new java.io.File(file.getLocation()+file.getFileName()));

    }

    @GetMapping(value = "/getFiles")
    public List<File> getFileList(){
        return fileService.getFileList();
    }

    @PostMapping(value = "/upload")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.saveFile(file);
    }

    @DeleteMapping(value="/delete")
    public boolean delete(@RequestBody File file){
     return fileService.deleteFile(file);



    }



}
