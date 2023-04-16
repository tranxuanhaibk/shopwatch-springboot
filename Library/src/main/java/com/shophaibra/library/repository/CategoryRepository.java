package com.shophaibra.library.repository;

import com.shophaibra.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
