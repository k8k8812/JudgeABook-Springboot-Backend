package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Review implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String detail;
	
	@ManyToOne
	@JoinColumn(name="book_id", referencedColumnName="id")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	public Review(Long id, @NotNull String detail, Book book, User user) {
		super();
		this.id = id;
		this.detail = detail;
		this.book = book;
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
