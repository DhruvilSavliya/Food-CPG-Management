package com.food.cpg.manufacturer.registration;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RegistrationController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_REGISTRATIONS_ROUTE = "registration/show-registrations";
    private static final String SHOW_REGISTRATIONS_END_POINT = "/show-registrations";

    @GetMapping("/show-registrations")
    public String showRegistrations(Registration registration, Model model) {
        List<Registration> registrations = registration.getAll();
        model.addAttribute("registrations", registrations);
        return SHOW_REGISTRATIONS_ROUTE;
    }

    @GetMapping("/approve-registration/{email}")
    public String approveManufacturer(@PathVariable("email") String email, Registration registration) {
        registration.approve(email);
        return redirectToShowRegistrations();
    }

    @GetMapping("/block-registration/{email}")
    public String blockManufacturer(@PathVariable("email") String email, Registration registration) {
        registration.block(email);
        return redirectToShowRegistrations();
    }

    private String redirectToShowRegistrations() {
        return REDIRECT_NOTATION + SHOW_REGISTRATIONS_END_POINT;
    }
}