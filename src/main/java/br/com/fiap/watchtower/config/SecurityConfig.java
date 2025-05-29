package br.com.fiap.watchtower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(r -> {
                    r.requestMatchers("/").permitAll();
                    r.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.successHandler(
                            (request,
                             response,
                             authentication) -> response.sendRedirect("/tester")
                    );
                })
                .formLogin(Customizer.withDefaults())
                .build();
    }
}

