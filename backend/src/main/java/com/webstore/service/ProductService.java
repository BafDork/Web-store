package com.webstore.service;

import com.webstore.dto.response.ProductResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Cart;
import com.webstore.model.Category;
import com.webstore.model.Product;
import com.webstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден с ID: " + productId));
    }

    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> findProductsByCategory(Long categoryId) {
        Set<Category> allSubCategories = categoryService.getCategoryWithAllSubCategories(categoryId);

        return productRepository.findByCategoriesIn(allSubCategories)
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
