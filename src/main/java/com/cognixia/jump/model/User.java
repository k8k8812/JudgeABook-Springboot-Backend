package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class User implements Serializable {

	public enum Role {
		ROLE_USER, ROLE_ADMIN
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<Review> reviews;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String username, String password, List<Review> reviews) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.reviews = reviews;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
}
