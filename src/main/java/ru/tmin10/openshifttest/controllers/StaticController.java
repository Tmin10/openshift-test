package ru.tmin10.openshifttest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {
    @RequestMapping(value={"/test", "/exit", "/error-page"})
    public String getStatic()
    {
        return "index.html";
    }
}
