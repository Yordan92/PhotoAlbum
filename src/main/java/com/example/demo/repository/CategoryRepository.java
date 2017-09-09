package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Picture;
import com.example.demo.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>{

	@EntityGraph(attributePaths = {"children"})
	Category findByNameAndOwner(String string, User owner);
	
	@EntityGraph(attributePaths = {"children"})
	Category findById(long id);
	
	@Query("SELECT pict FROM Picture pict where pict.category = ?1")
	List<Picture> findPicturesOfCategory(Category cat);
	
	@Modifying
	@Query("update Category u set u.name = ?1 where u.id = ?2")
	int renameCategory(String name, long id);



}
