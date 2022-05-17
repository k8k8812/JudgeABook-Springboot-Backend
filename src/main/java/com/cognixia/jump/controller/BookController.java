package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;

@RequestMapping("/api")
@RestController
public class BookController {

	@Autowired
	BookRepository bookRepo;
	
	@GetMapping("/book")
	public List<Book> getAllBooks() {
		
		return bookRepo.findAll();
	}
	
	
	@GetMapping("/book/{id}")
	public Book getBook(@PathVariable long id) throws ResourceNotFoundException {
		
		return bookRepo.findById(id).get();
	}
	
	
	@PostMapping("/book/add")
	public ResponseEntity<?> addNewBook(@RequestBody Book newBook) throws Exception {
		newBook.setId(-1L);
		Optional<Book> foundBook = bookRepo.findByName(newBook.getName());
		
		if (foundBook.isPresent()) {
			throw new Exception("Book already exists.");
		}
								
		bookRepo.save(newBook);
		
		return ResponseEntity.ok(newBook.getName() + " created.");
	}
	
	
	@DeleteMapping("/book/delete/{bookId}")
	public ResponseEntity<String> removeBookById(@PathVariable long bookId) throws ResourceNotFoundException {
		if(bookRepo.existsById(bookId)) {
			bookRepo.deleteById(bookId);
            return ResponseEntity.status(200).body("Deleted book with id = " + bookId);
        }
		else {    
            return ResponseEntity.status(400)
                    .body("Book with id = " + bookId + " does not exist");
        }
	}
	
	
}
