package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.PharmacyImage;
import com.example.onlinepharmacy.models.ProductImage;
import com.example.onlinepharmacy.repositories.PharmacyImageRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.services.abstracts.PharmacyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyImageServiceImpl implements PharmacyImageService {
    private final PharmacyImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final PharmacyRepository pharmacyRepository;


    public List<PharmacyImage> getAll() {
        return imageRepository.findAll();
    }

    public PharmacyImage getOne(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("This image does not found"));
    }

    public void upload(Long id, MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElseThrow(() -> new NotFoundException("Product does not exist"));
        if (bi == null) {
            throw new RuntimeException("The image is not valid!");
        }
        var result = cloudinaryService.upload(multipartFile);
        PharmacyImage image = PharmacyImage.builder()
                .pharmacy(pharmacy)
                .imageUrl(result.get("url").toString())
                .imageId(result.get("public_id").toString()).build();
        imageRepository.save(image);
    }

    public void delete(Long id) {
        PharmacyImage image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("This image does not found"));
        String cloudinaryId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.delete(image);
    }
}
