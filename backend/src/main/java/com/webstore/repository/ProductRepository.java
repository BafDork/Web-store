package com.webstore.repository;

import com.webstore.model.Category;
import com.webstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Поиск продуктов по категориям.
     *
     * @param categories список категорий
     * @return список продуктов
     */
    List<Product> findByCategoriesIn(Set<Category> categories);

    /**
     * Проверяет, связана ли категория с продуктами.
     *
     * @param category категория для проверки
     * @return true, если категория связана с продуктами, иначе false
     */
    boolean existsByCategoriesContaining(Category category);
}
