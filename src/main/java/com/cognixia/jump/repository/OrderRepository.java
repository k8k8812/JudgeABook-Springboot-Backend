package com.cognixia.jump.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Orders;
import com.cognixia.jump.model.User;

@Repository
@EntityScan(basePackages = {"com.cognixia.jump.model"})
public interface OrderRepository extends JpaRepository<Orders, Long> {

	ArrayList<Orders> findByUserId(Long userId);
	
	
}
