package com.webstore.controller;

import com.webstore.dto.response.CategoryResponseDTO;
import com.webstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/top-level")
    public ResponseEntity<List<CategoryResponseDTO>> getTopLevelCategories() {
        List<CategoryResponseDTO> categories = categoryService.getTopLevelCategories();
        return ResponseEntity.ok(categories);
    }
}
