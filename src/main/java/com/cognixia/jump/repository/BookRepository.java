package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognixia.jump.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByName(String name);

	Optional<Book> findByAuthor(String author);
	
	@Query(value = "SELECT b FROM Book b ORDER BY name")
	List<Book> findAllBooks();
	
}

