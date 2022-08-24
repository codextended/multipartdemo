package com.codextended.multipartdemo.service.serviceImpl;

import com.codextended.multipartdemo.model.UploadedFile;
import com.codextended.multipartdemo.repository.FileUploadRepository;
import com.codextended.multipartdemo.service.FileUploadService;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadImpl implements FileUploadService {
    private String uploadFolderPath = "/Users/dell/Desktop/uploaded_";

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public void uploadToLocal(MultipartFile file){
        byte[] data = new byte[0];
        try {
            data = file.getBytes();
            Path path = Paths.get(uploadFolderPath + file.getOriginalFilename());
            Files.write(path, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public UploadedFile uploadToDb(MultipartFile file) {
        UploadedFile uploadedFile = new UploadedFile();

        try {
            uploadedFile.setFileData(file.getBytes());
            uploadedFile.setFileType(file.getContentType());
            uploadedFile.setFileName(file.getOriginalFilename());
            return fileUploadRepository.save(uploadedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UploadedFile downloadFIle(String fileId) {
        UploadedFile uploadedFileToRet = fileUploadRepository.getOne(fileId);
        return uploadedFileToRet;
    }
}
