package com.webstore.service;

import com.webstore.dto.ProductDTO;
import com.webstore.model.Category;
import com.webstore.model.Product;
import com.webstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ProductDTO> findAllProducts(String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "price");
        return productRepository.findAll(sort)
                .stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductsByCategory(Long categoryId, String sortOrder) {
        Set<Category> allSubCategories = categoryService.getCategoryWithAllSubCategories(categoryId);

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "price");
        return productRepository.findByCategoriesIn(allSubCategories, sort)
                .stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscountPrice(),
                product.getStock(),
                product.getImageUrl()
        );
    }
}
