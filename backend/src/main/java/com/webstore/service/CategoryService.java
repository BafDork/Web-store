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

    /**
     * Получает категорию по её ID.
     * Если категория не найдена, выбрасывается исключение ResourceNotFoundException.
     *
     * @param categoryId ID категории
     * @return найденная категория
     */
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена с ID: " + categoryId));
    }

    /**
     * Получает все категории верхнего уровня (без родительской категории).
     *
     * @return список DTO объектов для категорий верхнего уровня
     */
    public List<CategoryResponseDTO> getTopLevelCategories() {
        List<Category> categories = categoryRepository.findByParentIsNullOrderByNameAsc();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Получает категорию и все её подкатегории (рекурсивно).
     *
     * @param categoryId ID категории
     * @return множество категории и всех её подкатегорий
     */
    public Set<Category> getCategoryWithAllSubCategories(Long categoryId) {
        Category category = getCategoryById(categoryId);
        Set<Category> allSubCategories = new HashSet<>();
        addSubCategories(category, allSubCategories);
        return allSubCategories;
    }

    /**
     * Рекурсивно добавляет подкатегории в множество.
     *
     * @param category текущая категория
     * @param subCategories множество подкатегорий
     */
    private void addSubCategories(Category category, Set<Category> subCategories) {
        subCategories.add(category);
        category.getSubCategories().forEach(subCategory -> addSubCategories(subCategory, subCategories));
    }
}
