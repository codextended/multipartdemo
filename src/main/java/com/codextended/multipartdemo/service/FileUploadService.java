package com.codextended.multipartdemo.service;

import com.codextended.multipartdemo.model.UploadedFile;
import com.codextended.multipartdemo.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public void uploadToLocal(MultipartFile file);
    public UploadedFile uploadToDb(MultipartFile file);
    public UploadedFile downloadFIle(String fileId);
}
