package com.enigmacamp.hellospring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface CourseUploadService {
    void uploadMaterial(MultipartFile multipartFile);

    Resource downloadMaterial(String id);
}
