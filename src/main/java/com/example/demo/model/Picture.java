package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "picture")
public class Picture extends GeneralEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5425205833741094144L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "path")
	private String path;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Picture(String name, String description, Date date, String path, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
		this.path = path;
		this.category = category;
	}
	
	public Picture() {
		super();
	}
	
}
