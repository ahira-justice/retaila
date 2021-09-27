package com.ahirajustice.app.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("")
@Hidden
public class HomeController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/retail/index.html");
    }

}
