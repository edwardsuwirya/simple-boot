package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.exception.ValidationException;
import com.enigmacamp.hellospring.model.request.FormDataWithFile;
import com.enigmacamp.hellospring.model.response.SuccessResponse;
import com.enigmacamp.hellospring.service.CourseUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/course-upload")

public class CourseUploadController {

    private CourseUploadService courseUploadService;

    public CourseUploadController(@Autowired CourseUploadService courseUploadService) {
        this.courseUploadService = courseUploadService;
    }

    @PostMapping
    public ResponseEntity upload(@Valid @ModelAttribute FormDataWithFile formDataWithFile, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        courseUploadService.uploadMaterial(formDataWithFile.getFile());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Course Material", "Success upload course"));
    }

    @GetMapping
    public ResponseEntity download(@RequestParam String id) {
        Resource file = courseUploadService.downloadMaterial(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
