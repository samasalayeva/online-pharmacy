package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> getAll();
    Image getOne(Long id);
    void upload(Long id, MultipartFile multipartFile) throws IOException;
    void delete(Long id);
}
