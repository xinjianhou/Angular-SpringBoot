package com.xinjian.wechat.config;

import com.xinjian.wechat.domain.Folder;
import com.xinjian.wechat.service.FileService;
import com.xinjian.wechat.service.FolderService;
import com.xinjian.wechat.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Value("${file.source}")
    private  String Source_DIR;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       // System.out.println(args.getOptionValues("init")+"--------------------");
        List<String> locations = FileUtil.list(new File(Source_DIR));
        List<Folder> folders = new ArrayList<>();
        if(!locations.isEmpty()){
            for (String location: locations){
                Folder folder = new Folder();
                folder.setLocation(location);
                folders.add(folder);
            }
        }
//        List<File> files = FileUtil.listAllFiles(Source_DIR,null);
//        List<com.xinjian.wechat.domain.File> fileList = new ArrayList();
//        if(!files.isEmpty()){
//            for(File f: files){
//                com.xinjian.wechat.domain.File file = new com.xinjian.wechat.domain.File();
//                file.setFileName(f.getName());
//                file.setFileSize(f.getTotalSpace());
//                file.setLocation(f.getAbsolutePath());
//                fileList.add(file);
//            }
//
//        }
//        if(!fileService.saveAll(fileList)){
//            throw  new Exception();
//        }

        if(!folderService.saveFolders(folders)){
            throw  new Exception();
        }
    }
}
