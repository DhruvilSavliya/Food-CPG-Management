package com.food.cpg.authentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String LOGIN_END_POINT = "/login";
    private static final String LOGIN_PAGE_ROUTE = "login";
    private static final String UNAUTHORIZED_ERROR_PAGE_ROUTE = "/403-error";
    private static final String VIEW_PROJECT_NAME_KEY = "projectName";

    @Value("${application.name}")
    private String projectName;

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute(VIEW_PROJECT_NAME_KEY, projectName);

        return REDIRECT_NOTATION + LOGIN_END_POINT;
    }

    @GetMapping("/login")
    public String login(AuthNavigator authNavigator, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        authNavigator.navigateToLandingPage(request, response);
        model.addAttribute(VIEW_PROJECT_NAME_KEY, projectName);

        return LOGIN_PAGE_ROUTE;
    }

    @GetMapping("/403-error")
    public String unauthorisedError(Model model) {
        return UNAUTHORIZED_ERROR_PAGE_ROUTE;
    }
}