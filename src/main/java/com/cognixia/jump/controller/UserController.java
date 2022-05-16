package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Orders;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@PostMapping("/register")
	//can use <?> here if you configure global exception handler
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


	
	//fix this so user can update only their own record
	
//	@PutMapping("/update/user")
//	public ResponseEntity<User> updateUser(@Valid @RequestBody User user, HttpServletRequest request) throws ResourceNotFoundException {
//		
//		final String authorizationHeader = request.getHeader("Authorization");
//		
//		String username = null;
//		String Jwt = null;
//		
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			Jwt = authorizationHeader.substring(7);
//			username = jwtUtil.extractUsername(Jwt);
//			User current = userRepo.findByUsername(username).get();
//		}
//		
//		if(!userRepo.existsById(user.getId())) {
//			throw new ResourceNotFoundException("User with id " + user.getId() + " does not exist.");
//		}
//		User savedUser = userRepo.save(user);
//		return new ResponseEntity<>(savedUser, HttpStatus.OK);	
//	
//	}
//	
	
	
	//   FIX THIS.  This method allows any user to update any user account.
	@PutMapping("/user/update")
	public ResponseEntity<String> updateUser(@Valid @RequestBody User user, HttpServletRequest request) throws ResourceNotFoundException {
		
		if(!userRepo.existsById(user.getId())) {
			throw new ResourceNotFoundException("User with id " + user.getId() + " does not exist.");
		}
		
		User savedUser = userRepo.save(user);
		return ResponseEntity.status(200).body("User with id = " + user.getId() + " updated");		
	}
	
	
	@PutMapping("/user/add/order/{userId}/{orderId}")
	public ResponseEntity<User> addOrderToUser(@PathVariable Long userId, @PathVariable Long orderId){
		
		User addToUser = userRepo.findById(userId).get();
		Orders orderToAdd = orderRepo.findById(orderId).get();
		orderToAdd.setUser(addToUser);
		
		orderRepo.save(orderToAdd);
		List<Orders> userOrdersList = addToUser.getOrders();
		userOrdersList.add(orderToAdd);
		orderRepo.save(orderToAdd);
		userRepo.save(addToUser);
		return null;
		
	}

	
	
	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<String> removeuser(@PathVariable long userId) throws ResourceNotFoundException {
		if(userRepo.existsById(userId)) {
			userRepo.deleteById(userId);
            return ResponseEntity.status(200).body("Deleted person with id = " + userId);
        }
		else {    
            return ResponseEntity.status(400)
                    .body("User with id = " + userId + " does not exist");
        }
	}
	
	
	@PutMapping("/user/addBookOrder/{userId}/{bookId}")
	public ResponseEntity<Orders> addNewOrderWithBookToUser(@PathVariable Long userId, Long bookId){
		Orders newOrder = new Orders();
		Book addBook = bookRepo.findById(bookId).get();
		
		newOrder.getBookOrders().add(addBook);
		User addToUser = userRepo.findById(userId).get();
		addToUser.getOrders().add(newOrder);
		//return null;
		return ResponseEntity.status(400).body(newOrder);
	}
	
}
