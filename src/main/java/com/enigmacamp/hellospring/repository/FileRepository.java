package com.enigmacamp.hellospring.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {
    void init();

    void store(MultipartFile file);

    Resource load(String filename);
}
