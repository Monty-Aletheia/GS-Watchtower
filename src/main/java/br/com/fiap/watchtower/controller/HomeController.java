package br.com.fiap.watchtower.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showDashboard() {
        return "redirect:/dashboard";
    }
        
}
