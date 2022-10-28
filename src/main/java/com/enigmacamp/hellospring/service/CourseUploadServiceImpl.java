package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.aspect.Loggable;
import com.enigmacamp.hellospring.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseUploadServiceImpl implements CourseUploadService {

    private FileRepository fileRepository;

    public CourseUploadServiceImpl(@Autowired FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Loggable
    @Override
    public void uploadMaterial(MultipartFile multipartFile) {
        fileRepository.store(multipartFile);
    }

    @Override
    public Resource downloadMaterial(String id) {
        return fileRepository.load(id);
    }
}
