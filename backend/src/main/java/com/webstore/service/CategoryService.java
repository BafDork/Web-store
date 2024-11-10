package com.webstore.service;

import com.webstore.dto.response.CategoryResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена с ID: " + categoryId));
    }

    public List<CategoryResponseDTO> getTopLevelCategories() {
        List<Category> categories = categoryRepository.findByParentIsNullOrderByNameAsc();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Set<Category> getCategoryWithAllSubCategories(Long categoryId) {
        Set<Category> allSubCategories = new HashSet<>();
        Category category = getCategoryById(categoryId);
        allSubCategories.add(category);
        getSubCategories(category, allSubCategories);
        return allSubCategories;
    }

    private void getSubCategories(Category category, Set<Category> subCategories) {
        for (Category subCategory : category.getSubCategories()) {
            subCategories.add(subCategory);
            getSubCategories(subCategory, subCategories);
        }
    }
}
