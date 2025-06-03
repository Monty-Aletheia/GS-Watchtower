package br.com.fiap.watchtower.controller;

import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.model.User;
import br.com.fiap.watchtower.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService service;

    public RegisterController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String createUser(UserRequestDTO userDTO) {
        service.create(userDTO);
        return "redirect:/login";
    }

}
