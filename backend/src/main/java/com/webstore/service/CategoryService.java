package com.webstore.service;

import com.webstore.dto.request.CategoryRequestDTO;
import com.webstore.dto.response.CategoryResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.repository.CategoryRepository;
import com.webstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

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
     * Получает категорию по её ID и преобразует её в DTO объект.
     *
     * @param categoryId ID категории
     * @return DTO объект для категории
     */
    public CategoryResponseDTO findCategoryById(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return new CategoryResponseDTO(category);
    }

    /**
     * Получает все категории.
     *
     * @return список DTO объектов для категорий
     */
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Получает все категории верхнего уровня (без родительской категории)
     * вместе с их подкатегориями рекурсивно.
     *
     * @return список DTO объектов для категорий верхнего уровня
     */
    public List<CategoryResponseDTO> getTopLevelCategories() {
        List<Category> topLevelCategories = categoryRepository.findByParentIsNullOrderByNameAsc();
        return topLevelCategories.stream()
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

    /**
     * Добавляет новую категорию.
     *
     * @param categoryRequestDTO запрос для создания категории
     * @return созданная категория
     */
    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category parentCategory = categoryRequestDTO.getParentId() != null
                ? getCategoryById(categoryRequestDTO.getParentId())
                : null;

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setParent(parentCategory);

        category = categoryRepository.save(category);

        return new CategoryResponseDTO(category);
    }

    /**
     * Получает список категорий по их ID.
     * Если категория не найдена, выбрасывается исключение ResourceNotFoundException.
     *
     * @param categoryIds список ID категорий
     * @return найденная категория
     */
    public List<Category> findCategoriesByIds(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("Одна или несколько категорий не найдены: " + categoryIds);
        }

        return categories;
    }

    /**
     * Удаляет категорию по её ID.
     *
     * @param categoryId идентификатор категории
     * @throws ResourceNotFoundException если категория не найдена
     * @throws IllegalStateException если есть связанные подкатегории или продукты
     */
    @Transactional
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория с ID " + categoryId + " не найдена"));

        if (!category.getSubCategories().isEmpty()) {
            throw new IllegalStateException("Невозможно удалить категорию с подкатегориями");
        }

        boolean hasProducts = productRepository.existsByCategoriesContaining(category);
        if (hasProducts) {
            throw new IllegalStateException("Невозможно удалить категорию, так как она связана с продуктами");
        }

        categoryRepository.delete(category);
    }

    /**
     * Обновляет категорию по её ID.
     *
     * @param categoryId идентификатор категории
     * @param categoryRequest запрос для обновления категории
     * @return обновленная категория
     */
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequest) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена с ID: " + categoryId));

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setParent(categoryRequest.getParentId() != null ? categoryRepository.findById(categoryRequest.getParentId()).orElse(null) : null);

        categoryRepository.save(existingCategory);
        return new CategoryResponseDTO(existingCategory);
    }
}
