package com.jetbrains.ali.photoz.clone.web;

import java.net.http.HttpHeaders;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jetbrains.ali.photoz.clone.model.Photo;
import com.jetbrains.ali.photoz.clone.service.PhotozService;

@RestController
public class DownloadController {

    private final PhotozService photozService;
    
    public DownloadController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id){
        Photo photo = photozService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        byte[] data = photo.getData();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition
            .builder("attachment")
            .filename(photo.getFileName())
            .build();
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType(photo.getContentType()))
            .body(data);
    }
}