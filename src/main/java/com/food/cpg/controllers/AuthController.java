package com.food.cpg.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.cpg.constants.TemplateConstants;
import com.food.cpg.models.Role;

/**
 * @author Kartik Gevariya
 */
@Controller
public class AuthController {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${application.name}")
    private String projectName;

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (final GrantedAuthority grantedAuthority : authorities) {
                String authorityName = grantedAuthority.getAuthority();
                Role userRole = Role.getRole(authorityName);
                if (userRole != null) {
                    String targetUrl = userRole.getLandingPage();

                    redirectStrategy.sendRedirect(request, response, targetUrl);
                }
            }
        }
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);

        return "login";
    }

    @GetMapping("/403-error")
    public String unauthorisedError(Model model) {
        return "403-error";
    }
}