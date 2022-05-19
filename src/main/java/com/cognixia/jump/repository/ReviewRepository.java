package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Optional<Review> findByUserId(Long id);

	List<Review> findByBookId(Long id);

}

