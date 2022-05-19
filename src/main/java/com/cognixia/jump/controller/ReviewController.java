package com.cognixia.jump.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
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
	UserController userController;
	
	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@GetMapping("/review/user/current")
	public List<Review> getReviewsByUserId(HttpServletRequest request){
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String Jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			Jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(Jwt);
			User current = userRepo.findByUsername(username).get();
			
			return current.getReviews();

		}
		return null;
		
	}
	

	//Check current user reviews for duplicate then assigns current user id to review
	@PostMapping("/review/add/{bookId}")
	public ResponseEntity<String> addReview(@RequestBody Review newReview, @PathVariable Long bookId, HttpServletRequest request){
		
		User current = userController.checkCurrentUser(request);
		
		//check if user has already reviewed this book
		for (Review review : current.getReviews()) {
			System.out.println(review.getBook().getId());
			if (review.getBook().getId() == bookId)
				return ResponseEntity.status(400).body("You have already reviewed this book!");
		}
		
		newReview.setBook(bookRepo.getById(bookId));
		newReview.setUser(current);
		reviewRepo.save(newReview);
			
		return ResponseEntity.status(200).body("Thanks for your review!");
	
	}
	
	
	@GetMapping("review/book/{bookId}")
	public List<Review> getReviewsByBook(@PathVariable Long bookId){
		
		return reviewRepo.findByBookId(bookId);
		
	}
	
	
	@PutMapping("review/update/{reviewId}")
	public ResponseEntity<String> updateReview(@RequestBody Review updateReview, HttpServletRequest request) throws ResourceNotFoundException{
		if(!reviewRepo.existsById(updateReview.getId())) {
			throw new ResourceNotFoundException("Review with id " + updateReview.getId() + " does not exist.");
		}
		
		reviewRepo.save(updateReview);
		return ResponseEntity.status(200).body("Review updated");	
	}
	
	
	//keeps giving error saying that there is no value present, I think it is talking about the value of current
//	@DeleteMapping("review/delete/{reviewId}")
//	public ResponseEntity<String> removeBookById(@PathVariable long reviewId, HttpServletRequest request) throws ResourceNotFoundException {
//		User current = userController.checkCurrentUser(request);
//		if (current == null)
//			System.out.println("current is null");
//		System.out.println(current.getUsername());
//		if(reviewRepo.existsById(reviewId) && reviewRepo.findById(reviewId).get().getUser().getId() == current.getId()) {
//			reviewRepo.deleteById(reviewId);
//            return ResponseEntity.status(200).body("Review deleted!");
//        }    
//        return ResponseEntity.status(400).body("Review with id = " + reviewId + " does not exist");
//	}
		
	//	works but users can delete other people's reviews
	@DeleteMapping("review/delete/{reviewId}")
	public ResponseEntity<String> removeBookById(@PathVariable long reviewId, HttpServletRequest request) throws ResourceNotFoundException {
		if(reviewRepo.existsById(reviewId)) {
			reviewRepo.deleteById(reviewId);
            return ResponseEntity.status(200).body("Review deleted!");
        }
		else {    
            return ResponseEntity.status(400).body("Review with id = " + reviewId + " does not exist");
        }
	}
	
}

