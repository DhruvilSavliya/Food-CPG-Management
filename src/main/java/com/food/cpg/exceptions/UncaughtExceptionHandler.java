package com.food.cpg.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Default controller advice responsible for handling
 * internal server errors/uncaught service exceptions
 *
 * @author Kartik Gevariya
 */
@ControllerAdvice
@RestController
public class UncaughtExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleServiceException(Exception ex, HttpServletRequest request) {
        LOG.error("Internal server Error: {}", ex.getMessage(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/server-error");
        return mav;
    }
}
