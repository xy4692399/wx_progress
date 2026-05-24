package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private OSSService ossService;

    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        try {
            String fileUrl = ossService.uploadFile(file);
            return Result.success(fileUrl);
        } catch (IOException e) {
            return Result.error("File upload failed: " + e.getMessage());
        }
    }
}