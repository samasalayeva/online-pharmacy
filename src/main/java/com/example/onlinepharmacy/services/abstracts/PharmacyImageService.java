package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.models.PharmacyImage;
import com.example.onlinepharmacy.models.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PharmacyImageService {
    List<PharmacyImage> getAll();
    PharmacyImage getOne(Long id);
    void upload(Long id, MultipartFile multipartFile) throws IOException;
    void delete(Long id);
}
