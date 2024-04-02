package com.example.onlinepharmacy.services.concrets;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.ProductImage;
import com.example.onlinepharmacy.repositories.ProductImageRepository;
import com.example.onlinepharmacy.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class ProductImageServiceImplTest {
    @Mock
    private ProductImageRepository imageRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductImageServiceImpl imageService;



    @Test
    void getAll_ShouldReturnAllImages() {
        // Given
        when(imageRepository.findAll()).thenReturn(List.of(new ProductImage(), new ProductImage()));

        // When
        List<ProductImage> result = imageService.getAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getOne_ShouldReturnImageById() {
        // Given
        Long id = 1L;
        ProductImage image = new ProductImage();
        when(imageRepository.findById(id)).thenReturn(Optional.of(image));

        // When
        ProductImage result = imageService.getOne(id);

        // Then
        assertSame(image, result);
    }

    @Test
    void delete_ShouldDeleteImage() throws IOException{
        // Given
        Long imageId = 1L;
        ProductImage image = new ProductImage();
        image.setImageId("cloudinary-image-id");
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        // When
        imageService.delete(imageId);

        // Then
        verify(cloudinaryService, times(1)).delete("cloudinary-image-id");
        verify(imageRepository, times(1)).delete(image);
    }

    @Test
    void delete_ShouldThrowException_WhenImageNotFound() {
        // Given
        Long imageId = 1L;
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NotFoundException.class, () -> imageService.delete(imageId));
    }
}