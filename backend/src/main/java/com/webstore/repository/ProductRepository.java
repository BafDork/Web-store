package com.webstore.repository;

import com.webstore.model.Category;
import com.webstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoriesIn(Set<Category> categories);
}
