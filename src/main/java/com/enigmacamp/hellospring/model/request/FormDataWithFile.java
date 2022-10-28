package com.enigmacamp.hellospring.model.request;

import com.enigmacamp.hellospring.util.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

public class FormDataWithFile {
    @ValidImage
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
