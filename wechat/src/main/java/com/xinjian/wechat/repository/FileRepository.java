package com.xinjian.wechat.repository;

import com.xinjian.wechat.domain.File;
import com.xinjian.wechat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select f from File f where f.fileName like :fileName")
    File findFileByFileName(@Param("fileName") String fileName);



    @Query("select f from File f where f.location like :location")
    File findFileByFilePath(@Param("location") String location);


}
