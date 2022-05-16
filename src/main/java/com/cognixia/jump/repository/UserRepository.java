package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	//Optional<User> findByUserId(Long userId);

	//Optional<User> findById(Long id);
	
	//void deleteById(Long id);
	
	//List<User> findAll();
	
}
