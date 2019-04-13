package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.Folder;
import com.xinjian.wechat.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${api.base-path}/folder", produces = MediaType.APPLICATION_JSON_VALUE)
public class FolderController {


    @Autowired
    private FolderService folderService;

    @GetMapping
    public List<Folder> getAllFolders(){
        return folderService.listAllFolders();
    }



}

