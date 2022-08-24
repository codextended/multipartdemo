package com.codextended.multipartdemo.controller;


import com.codextended.multipartdemo.model.UploadedFile;
import com.codextended.multipartdemo.response.FileUploadResponse;
import com.codextended.multipartdemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/local")
    public void uploadLocal(@RequestParam("file")MultipartFile file){
        fileUploadService.uploadToLocal(file);
    }

//    @PostMapping("/upload/db")
//    public void uploadDb(@RequestParam("file") MultipartFile file){
//        fileUploadService.uploadToDb(file);
//    }

    @PostMapping("/upload/db")
    public FileUploadResponse uploadDb(@RequestParam("file") MultipartFile file){
        UploadedFile uploadedFile = fileUploadService.uploadToDb(file);
        FileUploadResponse response = new FileUploadResponse();
        if (uploadedFile != null) {
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/v1/download/")
                    .path(uploadedFile.getFileId())
                    .toUriString();
            response.setDownloadUri(downloadUri);
            response.setFileId(uploadedFile.getFileId());
            response.setFileType(uploadedFile.getFileType());
            response.setUploadStatus(true);
            response.setMessage("File Uploaded Successfully");
            return response;
        }
        response.setMessage("Oops something went wrong. Please re upload!");
        return response;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        UploadedFile uploadedFileToRet = fileUploadService.downloadFIle(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename= " + uploadedFileToRet.getFileName())
                .body(new ByteArrayResource(uploadedFileToRet.getFileData()));
    }
}
