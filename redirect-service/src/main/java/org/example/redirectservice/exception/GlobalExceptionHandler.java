package org.example.redirectservice.exception;// package: org.example.redirectservice.exception

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleUrlNotFound(UrlNotFoundException ex) {
        ModelAndView mav = new ModelAndView("404");
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
