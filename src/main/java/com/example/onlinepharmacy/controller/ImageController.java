package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.models.Image;
import com.example.onlinepharmacy.services.abstracts.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAll() {
        return ResponseEntity.ok(imageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam Long id, @RequestParam MultipartFile multipartFile) {
        try {
            imageService.upload(id,multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Image successfully upload");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        imageService.delete(id);
        return ResponseEntity.ok("Image successfully removed");
    }
}
