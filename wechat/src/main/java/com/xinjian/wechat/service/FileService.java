package com.freshman.service;

import com.freshman.domain.File;
import com.freshman.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public boolean saveFile(MultipartFile mltFile) {
        try {
            File file = new File();
            file.setFileName(mltFile.getOriginalFilename());
            file.setFileSize(new Long(mltFile.getBytes().length));
            file.setLocation("/Users/xinjianhou/GIT/Angular-SpringBoot/wechat/target/");
            file.setUploadDate(new Date());
            mltFile.transferTo(new java.io.File(file.getLocation() + file.getFileName()));
            return (null != fileRepository.save(file)) ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    public List<File> getFileList() {
        return fileRepository.findAll();
    }

    public File getFileById(long id) {
        return fileRepository.getOne(id);
    }

    public boolean deleteFile(File file) {
        java.io.File f = new java.io.File(file.getLocation());
        if (f.exists()) {
            fileRepository.delete(file);
            return f.delete();
        } else {
           return false;
        }

    }

}
