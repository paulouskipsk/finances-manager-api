package br.edu.uftpr.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.uftpr.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select s from User s where s.username like ?1")
    Optional<User> findByUsername(String username);
	
}
