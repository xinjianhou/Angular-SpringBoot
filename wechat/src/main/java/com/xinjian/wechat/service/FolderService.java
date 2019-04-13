package com.xinjian.wechat.service;

import com.xinjian.wechat.domain.Folder;
import com.xinjian.wechat.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {


    @Autowired
    private FolderRepository folderRepository;

    public boolean saveFolders(List<Folder> folders) {
        List<Folder> folderList = folderRepository.saveAll(folders);
        if(folderList.containsAll(folders)){
            return true;
        }
        return false;

    }

    public List<Folder> listAllFolders(){
        return folderRepository.findAll();
    }
}
