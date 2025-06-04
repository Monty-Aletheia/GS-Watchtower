package br.com.fiap.watchtower.controller;

import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    private final UserService service;

    public RegisterController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRequestDTO("", "", ""));
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user") UserRequestDTO userDTO, 
                           BindingResult result, 
                           Model model) {
        if (result.hasErrors()) {
            System.err.println("Erro ao criar usuário: " + result.getAllErrors());
            model.addAttribute("user", userDTO);
            return "auth/register";
        }
        service.create(userDTO);
        return "redirect:/login";
    }

}
