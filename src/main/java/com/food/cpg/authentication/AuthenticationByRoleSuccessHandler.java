package com.food.cpg.authentication;

import java.io.IOException;
import java.util.Collection;
import java.util.Timer;

import com.food.cpg.inventory.Scheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.Manufacturer;

@Component
public class AuthenticationByRoleSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            Role userRole = Role.getRole(authorityName);

            if (userRole == Role.MANUFACTURER) {
                loadManufacturerSessionDetails(authentication);
            }

            String targetUrl = userRole.getLandingPage();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
    }

    private void loadManufacturerSessionDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails.getUsername();

        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        IManufacturerPersistence manufacturerPersistence = persistenceFactory.getManufacturerPersistence();
        Manufacturer manufacturer = manufacturerPersistence.get(loggedInUserEmail);

        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        authenticationSessionDetails.setAuthenticatedUserId(manufacturer.getId());
    }
}