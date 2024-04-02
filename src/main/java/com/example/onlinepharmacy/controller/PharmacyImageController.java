package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.models.PharmacyImage;
import com.example.onlinepharmacy.models.ProductImage;
import com.example.onlinepharmacy.services.abstracts.PharmacyImageService;
import com.example.onlinepharmacy.services.abstracts.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacy-images")
@RequiredArgsConstructor
public class PharmacyImageController {

    private final PharmacyImageService imageService;

    @PreAuthorize("hasRole('super_admin_role')")

    @GetMapping
    public ResponseEntity<List<PharmacyImage>> getAll() {
        return ResponseEntity.ok(imageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PharmacyImage> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getOne(id));
    }

    @PreAuthorize("hasRole('admin_client_role')")

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam Long id, @RequestParam MultipartFile multipartFile) {
        try {
            imageService.upload(id, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Image successfully upload");
    }

    @PreAuthorize("hasRole('admin_client_role')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity.ok("Image successfully removed");
    }

}
