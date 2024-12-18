package com.webstore.service;

import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Review;
import com.webstore.model.ReviewPhoto;
import com.webstore.repository.ReviewPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewPhotoService {

    private final ReviewPhotoRepository reviewPhotoRepository;
    private final ReviewService reviewService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Получение фото по его Id.
     *
     * @param photoId Id фото для получения
     * @return Массив байтов (содержимое файла)
     * @throws IOException Если файл не найден или не может быть прочитан
     */
    public byte[] getReviewPhoto(Long photoId) throws IOException {
        ReviewPhoto reviewPhoto = reviewPhotoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Фото отзыва не найдено с Id: " + photoId));
        Path photoPath = Paths.get(uploadPath, reviewPhoto.getPhotoUrl());
        return Files.readAllBytes(photoPath);
    }

    /**
     * Загрузка нового фото для отзыва.
     *
     * @param reviewId Id отзыва, к которому добавляется фото
     * @param file     Загружаемый файл
     * @return Сохранённый объект ReviewPhoto
     * @throws IOException Если произошла ошибка при сохранении файла
     */
    public ReviewPhoto uploadReviewPhoto(Long reviewId, MultipartFile file) throws IOException {
        Review review = reviewService.getReviewById(reviewId);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadPath, fileName);

        Files.copy(file.getInputStream(), path);

        ReviewPhoto reviewPhoto = ReviewPhoto.builder()
                .review(review)
                .photoUrl(fileName)
                .build();

        return reviewPhotoRepository.save(reviewPhoto);
    }

    /**
     * Удаление фото из базы данных и файловой системы.
     *
     * @param photoId Id фото для удаления
     */
    public void deletePhoto(Long photoId) {
        ReviewPhoto reviewPhoto = reviewPhotoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Фото отзыва не найдено с Id: " + photoId));

        Path photoPath = Paths.get(uploadPath, reviewPhoto.getPhotoUrl());
        try {
            Files.deleteIfExists(photoPath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении файла: " + reviewPhoto.getPhotoUrl(), e);
        }

        reviewPhotoRepository.deleteById(photoId);
    }
}


