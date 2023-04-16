package com.shophaibra.library.repository;

import com.shophaibra.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
