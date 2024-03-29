package com.shophaibra.customer.controller;

import com.shophaibra.library.dto.CategoryDto;
import com.shophaibra.library.model.Category;
import com.shophaibra.library.model.Product;
import com.shophaibra.library.service.CategoryService;
import com.shophaibra.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    public CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryDtoList);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        Long categoryId = product.getCategory().getId();
        List<Product> products = productService.getRelatedProducts(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "product-detail";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getProductsInCategory(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<Category> categories = categoryService.findAllActivated();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String filterLowPrice(Model model) {
        List<Category> categories = categoryService.findAllActivated();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterLowPrice();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-low-price";
    }
}
