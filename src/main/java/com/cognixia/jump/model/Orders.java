package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.cognixia.jump.repository.BookRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//private Long userId;
	private Float total;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	@ManyToMany
	@JoinTable(
		name = "book_orders",
		joinColumns = @JoinColumn(name = "order_id"),
		inverseJoinColumns = @JoinColumn(name = "book_id"))
	List<Book> bookOrders;


	public Orders(Long id, List<Book> bookOrders) {
		super();
		this.id = id;
		this.bookOrders = bookOrders;
		this.total = 0F;
	}

	public Orders() {
		this(-1L, new ArrayList<Book>());
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getTotal() {
		total = 0F;
		for(Book book: bookOrders) {
			total+=book.getPrice();
		}
		
		return total;
	}

	public void setTotal(Float total) {
		
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Book> getBookOrders() {
		return bookOrders;
	}

	public void setBookOrders(List<Book> bookOrders) {
		this.bookOrders = bookOrders;
	}
	
	
	
	
}
