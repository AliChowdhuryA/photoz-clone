package com.jetbrains.ali.photoz.clone.web;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.jetbrains.ali.photoz.clone.model.Photo;
import com.jetbrains.ali.photoz.clone.service.PhotozService;

import jakarta.validation.Valid;

@RestController
public class PhotozController {

    private final PhotozService photozService;

    public PhotozController(@Autowired PhotozService photozService) {
        this.photozService = photozService;
    }


    @GetMapping("/")
    public String hello(){
        return "Hello, World!";
    }

    @GetMapping("/photoz")
    public Collection<Photo> get()
    {
        return photozService.get();
    }
    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable String id)
    {
        Photo photo = photozService.get(id);
        if (photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return photo;
    }

    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable String id)
    {
        Photo photo = photozService.remove(id);
        if (photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photoz")
    public Photo create(@RequestPart("data")MultipartFile file) throws IOException{

        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }
}
