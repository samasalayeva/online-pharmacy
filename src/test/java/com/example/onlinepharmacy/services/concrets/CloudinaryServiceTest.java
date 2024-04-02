package com.example.onlinepharmacy.services.concrets;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class CloudinaryServiceTest {
    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Test
    void upload_ShouldUploadFileToCloudinary() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        Uploader uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.upload(any(), any())).thenReturn(Map.of("url", "https://example.com/image.jpg"));

        Map result = cloudinaryService.upload(multipartFile);

        verify(uploader, times(1)).upload(any(), any());
        assertEquals("https://example.com/image.jpg", result.get("url"));
    }

    @Test
    void delete_ShouldDeleteFileFromCloudinary() throws IOException {

        // Given
        String fileId = "test-file-id";
        Uploader uploader = Mockito.mock(Uploader.class);
        Cloudinary cloudinary = Mockito.mock(Cloudinary.class);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(fileId), any())).thenReturn(Map.of("result", "ok"));

        CloudinaryService cloudinaryService = new CloudinaryService(cloudinary);

        // When
        Map result = cloudinaryService.delete(fileId);

        // Then
        verify(uploader, times(1)).destroy(eq(fileId), any());
        assertEquals("ok", result.get("result"));
    }

}