package com.shophaibra.admin.controller;

import com.shophaibra.library.dto.ProductDto;
import com.shophaibra.library.model.Category;
import com.shophaibra.library.model.Product;
import com.shophaibra.library.service.CategoryService;
import com.shophaibra.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String product(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("title", "Manager Product");
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());
        return "/products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "/add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes) {
        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Add successfully!");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Add Failed!");
        }

        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Update product");
        List<Category> categories = categoryService.findAllActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes) {
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Update Failed!!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value= "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Enable successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Enable Failed!");
        }
        return "redirect:/products";
    }

    @RequestMapping(value= "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Delete successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Delete Failed!");
        }
        return "redirect:/products";
    }

    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("title", "Manager Product");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Search Result");
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "result-products";
    }
}
