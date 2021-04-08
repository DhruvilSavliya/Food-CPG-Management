package com.food.cpg.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthenticationController {

    private static final String APPLICATION_NAME_PROPERTY = "${application.name}";
    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String LOGIN_PAGE_ROUTE = "login";
    private static final String VIEW_PROJECT_NAME_KEY = "projectName";

    @Value(APPLICATION_NAME_PROPERTY)
    private String projectName;

    @GetMapping(AuthenticationEndpoint.ROOT_END_POINT)
    public String landingPage(Model model) {
        model.addAttribute(VIEW_PROJECT_NAME_KEY, projectName);

        return REDIRECT_NOTATION + AuthenticationEndpoint.LOGIN_END_POINT;
    }

    @GetMapping(AuthenticationEndpoint.LOGIN_END_POINT)
    public String login(AuthNavigator authNavigator, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        authNavigator.navigateToLandingPage(request, response);
        model.addAttribute(VIEW_PROJECT_NAME_KEY, projectName);

        return LOGIN_PAGE_ROUTE;
    }

    @GetMapping(AuthenticationEndpoint.UNAUTHORIZED_ERROR_PAGE_END_POINT)
    public String unauthorisedError() {
        return AuthenticationEndpoint.UNAUTHORIZED_ERROR_PAGE_END_POINT;
    }
}