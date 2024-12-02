package com.webstore.service.unit;

import com.webstore.dto.response.ProductResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.model.Product;
import com.webstore.repository.ProductRepository;
import com.webstore.service.CategoryService;
import com.webstore.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    /**
     * Тестирует получение продукта по ID.
     * Ожидается, что продукт будет найден.
     */
    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Тестирует получение продукта по ID.
     * Ожидается, что будет выброшено исключение, если продукт не найден.
     */
    @Test
    void getProductById_ShouldThrowException_WhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(productId)
        );
        assertEquals("Продукт не найден с ID: " + productId, exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Тестирует метод получения всех продуктов.
     * Ожидается возврат списка DTO для продуктов.
     */
    @Test
    void findAllProducts_ShouldReturnListOfProductDTOs() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    /**
     * Тестирует метод поиска продуктов по категории.
     * Ожидается возврат списка продуктов, относящихся к указанной категории.
     */
    @Test
    void findProductsByCategory_ShouldReturnListOfProductDTOs() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);

        Set<Category> allSubCategories = new HashSet<>();
        allSubCategories.add(category);

        List<Product> products = Arrays.asList(product1, product2);

        when(categoryService.getCategoryWithAllSubCategories(categoryId)).thenReturn(allSubCategories);
        when(productRepository.findByCategoriesIn(allSubCategories)).thenReturn(products);

        List<ProductResponseDTO> result = productService.findProductsByCategory(categoryId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryService, times(1)).getCategoryWithAllSubCategories(categoryId);
        verify(productRepository, times(1)).findByCategoriesIn(allSubCategories);
    }

    /**
     * Тестирует сохранение продукта.
     * Ожидается, что метод save будет вызван один раз с переданным продуктом.
     */
    @Test
    void saveProduct_ShouldCallSaveMethodOfRepository() {
        Product product = new Product();
        product.setId(1L);

        productService.saveProduct(product);

        verify(productRepository, times(1)).save(product);
    }
}
