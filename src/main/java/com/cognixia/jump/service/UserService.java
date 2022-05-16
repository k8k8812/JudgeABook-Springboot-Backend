package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	public List<User> getAllUsers() {
//		return repo.findAll();
//	}
	
	
//	public User addUser(User newUser) throws Exception {
//		
//		Optional<User> isAlreadyRegistered = userRepo.findByUsername(newUser.getUsername());
//		
//		if (isAlreadyRegistered.isPresent()) {
//			throw new Exception("User already exists.");
//		}
//		
//		String submittedPassword = newUser.getPassword();
//		
//		String encodedPassword = passwordEncoder.encode(submittedPassword);
//		
//		User registerUser = new User();
//		registerUser.setUsername(newUser.getUsername());
//		registerUser.setPassword(encodedPassword);
//		registerUser.setEnabled(true);
//		registerUser.setRole(Role.valueOf("ROLE_USER"));
//		
//		userRepo.save(registerUser);
//		
//		return registerUser;
//		
//	}
	
	
	public User updateUser(User user) throws ResourceNotFoundException {
		
		if(!userRepo.existsById(user.getId())) {
			throw new ResourceNotFoundException("User with id " + user.getId() + " does not exist.");
		}
		
		User savedUser = userRepo.save(user);
		return savedUser;
		
	}
//	
//	
//	public User deleteUser(User user) throws ResourceNotFoundException {
//		
//		Long id = user.getId();
//		User toDelete = userRepo.findById(id).get();
//		userRepo.deleteById(id);
//		
//		return toDelete;
//	}
	
}
