package com.example.demo.model;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "category", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"OWNER_ID", "name"})})
@NamedEntityGraphs({
	@NamedEntityGraph(name = "Category.children",
			attributeNodes = @NamedAttributeNode("children")),
	@NamedEntityGraph(name = "Category.pictures",
		attributeNodes = @NamedAttributeNode("pictures"))
})

public class Category extends GeneralEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="PARENT_ID", unique = false)
	private Category parent;
	
	@Transient
	private List<Map<String,String>> relativePath;
	
	@OneToMany(mappedBy="parent", fetch = FetchType.LAZY)
	private List<Category> children;
	
	@OneToOne
	@JoinColumn(name="OWNER_ID")
	private User owner;
	
	@JsonManagedReference
	@OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	private List<Picture> pictures;
	
	

	@Column(name = "name")
	private String name;
	
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Category> getChildren() {
		return children;
	}
	@JsonManagedReference
	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Map<String,String>> getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(List<Map<String,String>> relativePath) {
		this.relativePath = relativePath;
	}

	public Category(Category parent, String name, User owner) {
		super();
		this.parent = parent;
		this.name = name;
		this.owner = owner;
	}
	
	public Category() {
		super();
	}

	@Override
	public String toString() {
		return "Category [parent=" + parent + ", children="  + ", name=" + name + "]";
	}
	
	
	
}
