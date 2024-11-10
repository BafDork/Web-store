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
    private List<SubCategoryDTO> subCategories;

    public CategoryResponseDTO(Category category) {
        List<SubCategoryDTO> subCategories = category.getSubCategories().stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());

        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = subCategories;
    }

    @Data
    @AllArgsConstructor
    public static class SubCategoryDTO {
        private Long id;
        private String name;

        public SubCategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}
