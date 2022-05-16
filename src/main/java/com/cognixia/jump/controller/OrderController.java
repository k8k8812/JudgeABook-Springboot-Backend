package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Orders;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class OrderController {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/order")
	public List<Orders> getAllOrders() {
		
		return orderRepo.findAll();
	}
	
	
	@GetMapping("/order/user/current")
	public List<Orders> getOrdersByUserId(HttpServletRequest request){
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String Jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			Jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(Jwt);
			User current = userRepo.findByUsername(username).get();
			
			return current.getOrders();

		}
		return null;
		
	}	
	
	
	@PostMapping("/order/add/{userId}/{bookId}")
	public ResponseEntity<Orders> addNewOrderToUser(@PathVariable Long userId, Long bookId){
		Orders newOrder = new Orders();
		Book addBook = bookRepo.findById(bookId).get();
		ArrayList<Book> bookList = new ArrayList<Book>();
		bookList.add(addBook);
		
		newOrder.getBookOrders().add(addBook);
		userRepo.getById(userId).getOrders().add(newOrder);
				
		return null;
		
	}
	
	
	
	@PostMapping("/newOrder/add/{bookId}")
	public Orders newOrderWithBook(@PathVariable Long bookId){
		Orders newOrder = new Orders();
		Book addBook = bookRepo.findById(bookId).get();

		newOrder.getBookOrders().add(addBook);
		Orders saved = orderRepo.save(newOrder);
				
		return saved;
	}
	
	
	@PutMapping("/order/addBook/{orderId}/{bookId}")
	public Orders addBookToOrder(@PathVariable Long orderId, @PathVariable Long bookId){
		Orders addToOrder = orderRepo.findById(orderId).get();
		Book addBook = bookRepo.findById(bookId).get();
		
		List<Book> bookListToAddToOrder = addToOrder.getBookOrders();
		bookListToAddToOrder.add(addBook);
		addToOrder.setBookOrders(bookListToAddToOrder);
		Orders saved = orderRepo.save(addToOrder);
		//return ResponseEntity.ok("New order with book " + addBook.getName() + " created.");
		return saved;
	}
	
	
	
//	@PostMapping("/order/add/{userId}")
//	public ResponseEntity<Orders> addNewOrderToUser(@PathVariable Long userId){
//		Orders newOrder = new Orders();
//		
//		List<Orders> saveOrderList = userRepo.getById(userId).getOrders();
//		userRepo.getById(userId).getOrders().add(newOrder);
//		
//		
//		return null;
//		
//	}
	
	
	@DeleteMapping("/order/delete/{orderId}")
	public ResponseEntity<Orders> deleteOrder(@PathVariable Long orderId){
		orderRepo.deleteById(orderId);
		
		return null;
	}
	
}

