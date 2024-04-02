package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.ProductImage;
import com.example.onlinepharmacy.models.Product;
import com.example.onlinepharmacy.repositories.ProductImageRepository;
import com.example.onlinepharmacy.repositories.ProductRepository;
import com.example.onlinepharmacy.services.abstracts.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;

    public List<ProductImage> getAll() {
        return imageRepository.findAll();
    }

    public ProductImage getOne(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("This image does not found"));
    }

    public void upload(Long id, MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product does not exist"));
        if (bi == null) {
            throw new RuntimeException("The image is not valid!");
        }
        var result = cloudinaryService.upload(multipartFile);
        ProductImage image = ProductImage.builder()
                .product(product)
                .imageUrl(result.get("url").toString())
                .imageId(result.get("public_id").toString()).build();
        imageRepository.save(image);
    }

    public void delete(Long id) {
        ProductImage image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("This image does not found"));
        String cloudinaryId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.delete(image);
    }
}
