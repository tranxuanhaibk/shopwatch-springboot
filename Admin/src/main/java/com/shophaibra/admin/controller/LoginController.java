package com.shophaibra.admin.controller;

import com.shophaibra.library.dto.AdminDto;
import com.shophaibra.library.model.Admin;
import com.shophaibra.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto", new Admin());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String userName = adminDto.getUsername();
            Admin admin = adminService.findByUsername(userName);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Your email has been registerd!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminService.save(adminDto);
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Register successfully!");
            } else {
                adminService.save(adminDto);
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Password don't matching");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Can not register because error server");
        }
        return "register";
    }
}
