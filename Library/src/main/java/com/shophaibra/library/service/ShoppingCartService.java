package com.shophaibra.library.service;

import com.shophaibra.library.model.Customer;
import com.shophaibra.library.model.Product;
import com.shophaibra.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
}
