package com.shophaibra.admin.controller;

import com.shophaibra.library.dto.AdminDto;
import com.shophaibra.library.model.Admin;
import com.shophaibra.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminServiceImpl adminService;
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String userName = adminDto.getUsername();
            Admin admin = adminService.findByUsername(userName);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Your email has been registerd!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Password don't matching");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Can not register because error server");
        }
        return "register";
    }
}
