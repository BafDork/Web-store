package com.webstore.repository;

import com.webstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Поиск категорий верхнего уровня.
     *
     * @return список категорий верхнего уровня
     */
    List<Category> findByParentIsNullOrderByNameAsc();
}
