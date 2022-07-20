package com.senla.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MenuController {
    @RequestMapping("/menu")
    @ResponseBody
    public String menu() {
        return "menu";
    }
}
