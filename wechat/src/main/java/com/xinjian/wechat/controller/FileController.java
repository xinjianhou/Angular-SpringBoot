package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.File;
import com.xinjian.wechat.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/download")
    public FileSystemResource downloadFile(@RequestBody String path) {

        return new FileSystemResource(new java.io.File(path));

    }

    @GetMapping
    public List<File> getFileList() {
        return fileService.getFileList();
    }

    @PostMapping
    public boolean uploadFile(@RequestParam("file") final MultipartFile file) {
        return fileService.saveFile(file);
    }

    @DeleteMapping
    public boolean delete(@RequestBody final File file) {
        return fileService.deleteFile(file);


    }


}
