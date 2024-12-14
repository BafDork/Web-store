package com.webstore.dto.response;

import com.webstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<CategoryResponseDTO> subCategories;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = category.getSubCategories().stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }
}