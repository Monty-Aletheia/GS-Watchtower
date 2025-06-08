package br.com.fiap.watchtower.controller;

import br.com.fiap.watchtower.model.User;
import br.com.fiap.watchtower.service.MarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private MarkersService markersService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        logger.info("Acessando o dashboard");

        try {
            new Thread(() -> {
                try {
                    logger.info("Iniciando carregamento dos marcadores");
                    markersService.loadAllMarkers();
                    logger.info("Marcadores carregados com sucesso");
                } catch (Exception e) {
                    logger.error("Erro ao carregar marcadores: {}", e.getMessage(), e);
                }
            }).start();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = "Usuário";

            if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                name = oauth2User.getAttribute("name");
                logger.info("Usuário autenticado: {}", name);
            }

            model.addAttribute("name", name);
            return "dashboard";
        } catch (Exception e) {
            logger.error("Erro ao renderizar dashboard: {}", e.getMessage(), e);
            throw e;
        }
    }
}
