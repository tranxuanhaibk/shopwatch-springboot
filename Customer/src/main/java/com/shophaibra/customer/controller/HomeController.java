package com.shophaibra.customer.controller;

import com.shophaibra.library.dto.ProductDto;
import com.shophaibra.library.model.Category;
import com.shophaibra.library.model.Customer;
import com.shophaibra.library.service.CategoryService;
import com.shophaibra.library.service.CustomerService;
import com.shophaibra.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        if(principal != null){
            session.setAttribute("username", principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }
}
