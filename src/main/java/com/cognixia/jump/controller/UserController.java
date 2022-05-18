package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> createNewUser(@RequestBody User newUser) throws Exception {
		
		Optional<User> isAlreadyRegistered = userRepo.findByUsername(newUser.getUsername());
		
		if (isAlreadyRegistered.isPresent()) {
			throw new Exception("User already exists.");
		}
		
		String submittedPassword = newUser.getPassword();
		
		String encodedPassword = passwordEncoder.encode(submittedPassword);
		
		User registerUser = new User();
		registerUser.setUsername(newUser.getUsername());
		registerUser.setPassword(encodedPassword);
		registerUser.setEnabled(true);
		registerUser.setRole(Role.valueOf("ROLE_USER"));
		
		userRepo.save(registerUser);
				
		return ResponseEntity.ok(newUser.getUsername() + " created.");
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String JWT = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(JWT));
		
	}
	

	@GetMapping("/user")
	public List<User> getAllusers() {
		
		return userRepo.findAll();
	}
	
	
	@GetMapping("/user/{id}")
	public User getuser(@PathVariable long id) throws ResourceNotFoundException {
		
		return userRepo.findById(id).get();
	}
	
	
	@GetMapping("/user/current")
	public User checkCurrentUser(HttpServletRequest request) {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String Jwt = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			Jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(Jwt);
		}
		
		return userRepo.findByUsername(username).get();
		
	}	
	
}

