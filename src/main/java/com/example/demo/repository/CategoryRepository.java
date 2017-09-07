package com.example.demo.repository;

import com.example.demo.model.Category;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>{

	@EntityGraph(value = "Category.subCategories", type = EntityGraphType.LOAD)
	Category findByName(String string);

	@Modifying
	@Query("update Category u set u.name = ?1 where u.id = ?2")
	int renameCategory(String name, long id);



}
