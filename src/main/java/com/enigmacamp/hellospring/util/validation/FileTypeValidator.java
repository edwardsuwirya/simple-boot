package com.enigmacamp.hellospring.util.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;

        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            result = false;
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
