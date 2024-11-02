package com.webstore.dto;

import com.webstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private List<SubCategoryDTO> subCategories;

    public CategoryDTO(Category category) {
        List<SubCategoryDTO> subCategories = category.getSubCategories().stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());

        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = subCategories;
    }

    public class SubCategoryDTO {
        private Long id;
        private String name;

        public SubCategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}
