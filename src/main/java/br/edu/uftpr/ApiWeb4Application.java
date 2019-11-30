package br.edu.uftpr;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.repository.UserRepository;

@SpringBootApplication
public class ApiWeb4Application implements CommandLineRunner  {
	
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiWeb4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "Everton", "everton", "123");
		User u2 = new User(null, "Luciana", "luciana", "456");
		User u3 = new User(null, "Isabela", "isabela", "789");
		User u4 = new User(null, "Gabriel", "gabriel", "159");
		
		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));	
	}
	
	

}
