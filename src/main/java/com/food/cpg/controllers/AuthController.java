package com.food.cpg.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * @author Kartik Gevariya
 */
@Controller
public class AuthController {

    @Value("${application.name}")
    private String projectName;

    /*@GetMapping("/")
    public String login(Model model) {
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);

        return "login";
    }*/
    @RolesAllowed("USER")
    @RequestMapping("/user")
    public String getUser()
    {
        return "vendors";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping("/admin")
    public String getAdmin()
    {
        return "admin_login";
    }

}