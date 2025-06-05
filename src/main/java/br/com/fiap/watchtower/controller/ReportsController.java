package br.com.fiap.watchtower.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportsController {

    @GetMapping("/reports")
    public String reports(Model model, Authentication authentication) {
        // Adiciona o nome do usuário ao modelo
        if (authentication != null) {
            String name = authentication.getName();
            model.addAttribute("name", name);
        }
        
        return "reports";
    }
} 