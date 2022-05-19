package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByName(String name);

	Optional<Book> findByAuthor(String author);
	
}

