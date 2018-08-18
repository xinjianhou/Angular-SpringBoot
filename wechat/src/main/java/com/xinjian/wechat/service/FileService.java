package com.xinjian.wechat.service;

import com.xinjian.wechat.domain.File;
import com.xinjian.wechat.repository.FileRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private FileRepository fileRepository;

    public boolean saveFile(MultipartFile mltFile) {
        File file = mapper.map(mltFile, File.class);
        file.setLocation("target");
        try {
            mltFile.transferTo(new java.io.File(file.getLocation() + file.getFileName()));
            return (null != fileRepository.save(file)) ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }




    }

}
