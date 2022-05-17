package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
