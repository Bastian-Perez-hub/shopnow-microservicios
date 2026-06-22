package com.example.moduloPedidos;

import com.example.moduloPedidos.model.Role;
import com.example.moduloPedidos.model.User;
import com.example.moduloPedidos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShopNowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopNowApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("admin").isEmpty()) {
				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("1234"));
				user.setRole(Role.ROLE_ADMIN);
				repo.save(user);
			}
		};
	}
}
