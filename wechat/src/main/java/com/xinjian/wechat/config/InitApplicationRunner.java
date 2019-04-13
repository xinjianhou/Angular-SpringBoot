package com.xinjian.wechat.config;

import com.xinjian.wechat.domain.Folder;
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
        if(!folderService.saveFolders(folders)){
            throw  new Exception();
        }
    }
}
