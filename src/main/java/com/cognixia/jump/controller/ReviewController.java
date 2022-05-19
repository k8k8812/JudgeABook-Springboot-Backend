package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RequestMapping("/api")
@RestController
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
//	@GetMapping("/review/user/current")
//	public List<Review> getOrdersByUserId(HttpServletRequest request){
//		
//		final String authorizationHeader = request.getHeader("Authorization");
//		
//		String username = null;
//		String Jwt = null;
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			
//			Jwt = authorizationHeader.substring(7);
//			username = jwtUtil.extractUsername(Jwt);
//			User current = userRepo.findByUsername(username).get();
//			
//			return current.getReviews();
//
//		}
//		return null;
//		
//	}
	
	
//	@PostMapping("/review/add/{userId}/{bookId}")
//	public ResponseEntity<Review> addReview(@PathVariable Long userId, Long bookId){
//		Review newReview = new Review();
//		Book reviewBook = bookRepo.findById(bookId).get();
//		//ArrayList<Book> bookList = new ArrayList<Book>();
//		//bookList.add(addBook);
//		
//		newReview.getBookOrders().add(addBook);
//		userRepo.getById(userId).getOrders().add(newReview);
//				
//		return null;
//		
//	}
	

	@PostMapping("/review/add/{userId}/{bookId}")
	public ResponseEntity<String> addReview(@RequestBody Review newReview, @PathVariable Long bookId, @PathVariable Long userId){
		
		
		
		newReview.setBook(bookRepo.findById(bookId).get());
		newReview.setUser(userRepo.findById(userId).get());

		reviewRepo.save(newReview);
				
		return ResponseEntity.ok("New review posted.");		
	}
	
	
	
}

