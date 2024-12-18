package com.webstore.controller;

import com.webstore.model.ReviewPhoto;
import com.webstore.service.ReviewPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ReviewPhotoController {

    private final ReviewPhotoService reviewPhotoService;

    @GetMapping("/api/review-photos/{reviewId}")
    public ResponseEntity<byte[]> getReviewPhoto(@PathVariable Long reviewId) {
        try {
            byte[] photo = reviewPhotoService.getReviewPhoto(reviewId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/api/review-photos/{reviewId}")
    public ResponseEntity<String> uploadReviewPhoto(@PathVariable Long reviewId,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            ReviewPhoto uploadedPhoto = reviewPhotoService.uploadReviewPhoto(reviewId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully with ID: " + uploadedPhoto.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @DeleteMapping("admin/review-photos/delete/{photoId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId) {
        reviewPhotoService.deletePhoto(photoId);
        return ResponseEntity.ok("Фото удалено");
    }
}

