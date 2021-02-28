package com.food.cpg.controllers;

import com.food.cpg.models.User;
import com.food.cpg.services.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.food.cpg.constants.TemplateConstants;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Kartik Gevariya
 */
@Controller
public class AuthController {

    @Value("${application.name}")
    private String projectName;


    @Autowired
    private IUserRepository userRepo;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);

        return "login";
    }
}