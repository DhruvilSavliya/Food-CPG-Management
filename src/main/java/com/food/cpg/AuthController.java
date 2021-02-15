package com.food.cpg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.food.cpg.constants.TemplateConstants;

/**
 * @author Kartik Gevariya
 */
@Controller
public class AuthController {

    @Value("${application.name}")
    private String projectName;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);

        return "login";
    }
}