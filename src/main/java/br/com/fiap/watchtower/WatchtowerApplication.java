package br.com.fiap.watchtower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class WatchtowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchtowerApplication.class, args);
	}

}
