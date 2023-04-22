package com.shophaibra.library.service;

import com.shophaibra.library.dto.CategoryDto;
import com.shophaibra.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);
    List<Category> findAllActivated();

    /*Customer*/
    List<CategoryDto> getCategoryAndProduct();
}
