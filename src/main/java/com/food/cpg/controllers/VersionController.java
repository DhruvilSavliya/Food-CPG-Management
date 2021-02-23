package com.food.cpg.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.food.cpg.constants.TemplateConstants;

/**
 * @author Kartik Gevariya
 */
@Controller
public class VersionController {

    @Value("${application.version}")
    private String projectVersion;

    @Value("${application.name}")
    private String projectName;

    @GetMapping("/version")
    public String version(Model model) {
        model.addAttribute(TemplateConstants.PROJECT_NAME, projectName);
        model.addAttribute(TemplateConstants.PROJECT_VERSION, projectVersion);

        return "version";
    }
}