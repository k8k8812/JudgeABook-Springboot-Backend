package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Book;

@Repository
//@EntityScan(basePackages = {"com.cognixia.jump.model"})

public interface BookRepository extends JpaRepository<Book, Long> {
	
	//Optional<Book> findById(int id);
	
	Optional<Book> findByName(String name);
	
	//Boolean existsByName(String name);

}
