package br.com.fiap.watchtower.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) throws JsonProcessingException {
        List<Map<String, Object>> pontos = new ArrayList<>();

        Map<String, Object> p1 = Map.of(
                "latitude", -23.5505,
                "longitude", -46.6333,
                "description", "São Paulo",
                "riskLevel", "MEDIUM"
        );

        Map<String, Object> p2 = Map.of(
                "latitude", -22.9068,
                "longitude", -43.1729,
                "description", "Rio de Janeiro",
                "riskLevel", "MEDIUM"
        );

        Map<String, Object> p3 = Map.of(
                "latitude", -15.7939,
                "longitude", -47.8828,
                "description", "Brasília",
                "riskLevel", "MEDIUM"
        );

        pontos.add(p1);
        pontos.add(p2);
        pontos.add(p3);

        ObjectMapper objectMapper = new ObjectMapper();
        String pontosJson = objectMapper.writeValueAsString(pontos);
        model.addAttribute("pontos", pontosJson);

        // Obtendo autenticação genérica
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                List<String> fullName = List.of(oauthUser.getAttributes().get("name").toString().split(" "));
                String name = fullName.size() >= 2 ? fullName.get(0) + " " + fullName.get(1) : fullName.get(0);
                model.addAttribute("name", name);
                model.addAttribute("photo", oauthUser.getAttributes().get("picture"));
            } else {
                // Usuário autenticado normalmente (por exemplo, com username e senha)
                String username = authentication.getName();
                model.addAttribute("name", username);
                model.addAttribute("photo", "/img/default-user.png"); // Uma imagem padrão
            }
        } else {
            // Usuário não autenticado
            model.addAttribute("name", "Visitante");
            model.addAttribute("photo", "/img/default-user.png");
        }

        return "dashboard";
    }
}
