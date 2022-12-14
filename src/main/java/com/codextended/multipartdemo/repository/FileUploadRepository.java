package com.codextended.multipartdemo.repository;

import com.codextended.multipartdemo.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadedFile, String> {
}
