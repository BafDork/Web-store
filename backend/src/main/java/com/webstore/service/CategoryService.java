package com.webstore.service;

import com.webstore.dto.CategoryDTO;
import com.webstore.dto.SubCategoryDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAllTopLevelCategories() {
        List<Category> categories = categoryRepository.findByParentIsNullOrderByNameAsc();
        return categories.stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    public Set<Category> getCategoryWithAllSubCategories(Long categoryId) {
        Set<Category> allSubCategories = new HashSet<>();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
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

    private CategoryDTO convertToCategoryDTO(Category category) {
        List<SubCategoryDTO> subCategories = category.getSubCategories().stream()
                .map(subCategory -> new SubCategoryDTO(subCategory.getId(), subCategory.getName()))
                .collect(Collectors.toList());

        return new CategoryDTO(category.getId(), category.getName(), subCategories);
    }
}
