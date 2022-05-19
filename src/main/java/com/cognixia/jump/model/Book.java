package com.cognixia.jump.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.cognixia.jump.model.User.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="book")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column
	private String name;
	
	@Column
	private String genre;
	
	@Column
	private String author;
	
	@Column
	private String description;
	
	@Column
	private String rating;
	
	@Column
	private String image;

	@JsonManagedReference
	@OneToMany(mappedBy="book", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> reviews;
		
	
	public Book() {
		this(-1L, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", new ArrayList<Review>());
	}



	public Book(Long id, @NotBlank String name, String genre, String author, String description, String rating,
			String image, List<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.author = author;
		this.description = description;
		this.rating = rating;
		this.image = image;
		this.reviews = reviews;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}

	@JsonIgnore
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
}

