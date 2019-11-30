package br.edu.uftpr.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}

	public User save(User user) throws DataIntegrityViolationException {
        return userRepository.save(user);
    }
	
	public List<User> saveAll(List<User> users) throws DataIntegrityViolationException {
        return userRepository.saveAll(users);
    }
	
	public void delete(Long id) throws DataIntegrityViolationException {
        userRepository.deleteById(id);
    }
	

}
