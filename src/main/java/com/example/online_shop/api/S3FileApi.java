package com.example.online_shop.api;

import com.example.online_shop.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/s3_file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "S3 File API")
public class S3FileApi {

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload File To AWS", description = "This method uploads files to AWS")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(storageService.upload(multipartFile), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "Delete File From AWS", description = "This method deletes files from AWS")
    public ResponseEntity<Map<String, String>> deleteFile(@RequestParam String fileLink) {
        return new ResponseEntity<>(storageService.delete(fileLink), HttpStatus.OK);
    }
}