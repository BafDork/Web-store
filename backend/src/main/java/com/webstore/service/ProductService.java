package com.webstore.service;

import com.webstore.dto.response.ProductResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Category;
import com.webstore.model.Product;
import com.webstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    /**
     * Возвращает продукт по его ID.
     *
     * @param productId идентификатор продукта
     * @return объект продукта
     * @throws ResourceNotFoundException если продукт с указанным ID не найден
     */
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден с ID: " + productId));
    }

    /**
     * Получает продукт по его ID и преобразует его в DTO-объект.
     *
     * @param productId идентификатор продукта
     * @return объект ProductResponseDTO
     */
    public ProductResponseDTO findProductById(Long productId) {
        Product product = getProductById(productId);
        return new ProductResponseDTO(product);
    }

    /**
     * Получает список всех продуктов и преобразует их в DTO-объекты.
     *
     * @return список объектов ProductResponseDTO
     */
    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Получает список продуктов, принадлежащих заданной категории или ее подкатегориям.
     *
     * @param categoryId идентификатор категории
     * @return список объектов ProductResponseDTO
     */
    public List<ProductResponseDTO> findProductsByCategory(Long categoryId) {
        Set<Category> allSubCategories = categoryService.getCategoryWithAllSubCategories(categoryId);

        return productRepository.findByCategoriesIn(allSubCategories)
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет продукт в базе данных.
     *
     * @param product объект продукта для сохранения
     */
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
