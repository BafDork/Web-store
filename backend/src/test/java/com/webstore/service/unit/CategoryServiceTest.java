package com.webstore.service.unit;

import com.webstore.dto.response.CategoryResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.model.Product;
import com.webstore.repository.CategoryRepository;
import com.webstore.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category parentCategory;
    private Category childCategory1;
    private Category childCategory2;

    @BeforeEach
    void setUp() {
        Set<Product> products = new HashSet<>();
        products.add(new Product());
        products.add(new Product());

        parentCategory = new Category(1L, "Parent Category", null, new HashSet<>(), products);
        childCategory1 = new Category(2L, "Child Category 1", parentCategory, new HashSet<>(), new HashSet<>());
        childCategory2 = new Category(3L, "Child Category 2", parentCategory, new HashSet<>(), new HashSet<>());

        parentCategory.setSubCategories(new HashSet<>());
        parentCategory.getSubCategories().add(childCategory1);
        parentCategory.getSubCategories().add(childCategory2);
    }

    /**
     * Тестирует метод getCategoryById, проверяя, что выбрасывается исключение ResourceNotFoundException, если категория не найдена.
     */
    @Test
    void getCategoryById_ShouldThrowResourceNotFoundException_WhenCategoryNotFound() {
        Long categoryId = 999L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.getCategoryById(categoryId));

        assertEquals("Категория не найдена с ID: " + categoryId, exception.getMessage());
    }

    /**
     * Тестирует метод getCategoryById, проверяя, что возвращается категория по id.
     */
    @Test
    void getCategoryById_ShouldReturnCategory_WhenCategoryFound() {
        Long categoryId = parentCategory.getId();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(parentCategory));

        Category category = categoryService.getCategoryById(categoryId);

        assertNotNull(category);
        assertEquals(parentCategory.getId(), category.getId());
    }

    /**
     * Тестирует метод getTopLevelCategories, проверяя, что возвращаются категории верхнего уровня.
     */
    @Test
    void getTopLevelCategories_ShouldReturnTopLevelCategories() {
        when(categoryRepository.findByParentIsNullOrderByNameAsc()).thenReturn(Collections.singletonList(parentCategory));

        List<CategoryResponseDTO> categories = categoryService.getTopLevelCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(parentCategory.getName(), categories.get(0).getName());
    }

    /**
     * Тестирует метод getCategoryWithAllSubCategories, проверяя, что возвращаются категория и все её подкатегории.
     */
    @Test
    void getCategoryWithAllSubCategories_ShouldReturnCategoryWithSubCategories() {
        Long categoryId = parentCategory.getId();
        Set<Category> expectedCategories = new HashSet<>();
        expectedCategories.add(parentCategory);
        expectedCategories.add(childCategory1);
        expectedCategories.add(childCategory2);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(parentCategory));

        Set<Category> resultCategories = categoryService.getCategoryWithAllSubCategories(categoryId);

        assertNotNull(resultCategories);
        assertEquals(expectedCategories.size(), resultCategories.size());
        assertTrue(resultCategories.containsAll(expectedCategories));
    }
}