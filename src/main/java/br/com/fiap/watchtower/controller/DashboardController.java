package br.com.fiap.watchtower.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, OAuth2AuthenticationToken token) throws JsonProcessingException {
        List<Map<String, Object>> pontos = new ArrayList<>();

        Map<String, Object> p1 = Map.of(
                "lat", -23.5505,
                "lon", -46.6333,
                "nome", "São Paulo"
        );

        Map<String, Object> p2 = Map.of(
                "lat", -22.9068,
                "lon", -43.1729,
                "nome", "Rio de Janeiro"
        );

        Map<String, Object> p3 = Map.of(
                "lat", -15.7939,
                "lon", -47.8828,
                "nome", "Brasília"
        );

        pontos.add(p1);
        pontos.add(p2);
        pontos.add(p3);

        ObjectMapper objectMapper = new ObjectMapper();
        String pontosJson = objectMapper.writeValueAsString(pontos);

        model.addAttribute("pontos", pontosJson);
        List<String> fullName = List.of(token.getPrincipal().getAttributes().get("name").toString().split(" "));
        String name = fullName.get(0) + " " + fullName.get(1);
        model.addAttribute("name", name);
        model.addAttribute("photo",  token.getPrincipal().getAttributes().get("picture"));

        return "dashboard";
    }
}
