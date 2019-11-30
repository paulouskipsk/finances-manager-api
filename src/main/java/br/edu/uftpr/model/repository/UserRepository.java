package br.edu.uftpr.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.uftpr.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
