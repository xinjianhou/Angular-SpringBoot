package com.xinjian.wechat.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    MultipartFile file = null;

    @Mock
    FileService fileService;

    @Before
    public void setUp() throws Exception {
       file = new MultipartFile() {
            @Override
            public String getName() {
                return "test";
            }

            @Override
            public String getOriginalFilename() {
                return "test";
            }

            @Override
            public String getContentType() {
                return "txt";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveFile() {


        fileService.saveFile(file);
        fileService.getFileById(1);
        Assert.assertEquals(1,1);
    }

    @Test
    public void getFileList() {
    }

    @Test
    public void getFileById() {
    }

    @Test
    public void deleteFile() {
    }
}