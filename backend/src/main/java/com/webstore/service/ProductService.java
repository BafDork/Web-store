//package com.webstore.service;
//
//import com.webstore.model.Product;
//import com.webstore.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductService {
//    @Autowired
//    private ProductRepository productRepository;
//
//    public List<Product> getProductsByCategory(Long categoryId) {
//        return productRepository.findByCategoryId(categoryId);
//    }
//}