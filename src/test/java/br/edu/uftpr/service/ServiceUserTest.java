package br.edu.uftpr.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ServiceUserTest {
	@Autowired
	UserService userService;

	@Test
	//TEST 1
	public void saveAllUsersTest() {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "Everton", "everton", "124653"));
		users.add(new User(2L, "Luciana", "luciana", "4446465"));
		users.add(new User(3L, "Isabela", "isabela", "78787989"));
		users.add(new User(4L, "Sheila", "sheila", "145448559"));
		users.add(new User(5L, "Cristhian", "Cristhian", "1545659"));
		users.add(new User(6L, "Rafael", "Rafael", "128484859"));
		users.add(new User(7L, "Roberto", "Roberto", "154585859"));
		userService.saveAll(users);

		List<User> usrs = userService.findAll();
		assertFalse(usrs.isEmpty());
		
		Optional<User> usr = userService.findById(4L);

		Assertions.assertThat(usr).isPresent();
		Assertions.assertThat(usr.get().getName()).isEqualTo("Sheila");
		Assertions.assertThat(usr.get().getUsername()).isEqualTo("sheila");
		Assertions.assertThat(usr.get().getPassword()).isEqualTo("145448559");
	}

	@Test
	//TEST 2
	public void saveUserTest() {
		User user = new User(8L, "Jose", "jose", "15454579");
		userService.save(user);

		Optional<User> usr = userService.findByUsername("jose");

		Assertions.assertThat(usr.get().getName()).isEqualTo("Jose");
		Assertions.assertThat(usr.get().getUsername()).isEqualTo("jose");
		Assertions.assertThat(usr.get().getPassword()).isEqualTo("15454579");
	}

	@Test
	//TEST 3
	public void updateUserTest() {
		User user = new User(2L, "joao", "shirley", "abc123");
		userService.save(user);
		
		userService.save(new User(2L, "Shirley", "shirley", "abc123"));
		Optional<User> usr = userService.findById(2L);

		Assertions.assertThat(usr).isNotEmpty();
		Assertions.assertThat(usr.get().getUsername()).isNotEqualTo("shirley");
		Assertions.assertThat(usr.get().getName()).isEqualTo("Shirley");
		Assertions.assertThat(usr.get().getPassword()).isEqualTo("abc123");
	}

//	@Test
//	//TEST 4
//	public void deleteUserByIdTest() {
//		User user = new User(20L, "Jjjj", "jjjj", "15454579");
//		userService.save(user);
//
//		Optional<User> usr = userService.findByUsername("jjjj");
//
//		Assertions.assertThat(usr.get().getName()).isEqualTo("Jjjj");
//		Assertions.assertThat(usr.get().getUsername()).isEqualTo("jjjj");
//		Assertions.assertThat(usr.get().getPassword()).isEqualTo("15454579");
//		
//		
//		
//		
//		
//		
//		userService.delete(20L);
//		Assertions.assertThat(userService.findById(10L)).isEmpty();
//	}

}
