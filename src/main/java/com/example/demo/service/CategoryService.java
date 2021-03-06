package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.Category;
import com.example.demo.model.Picture;
import com.example.demo.model.User;

public interface CategoryService {

	void renameCategory(long category_id, String name, User owner);
	void deleteCategory(long category_id, User owner);
	void addPictureToCategory(long category_id, User owner, Picture picture);
	void addCategory(long parentId, Category cat, User owner);
	Category findByNameAndOwner(String name, User owner);
	Category findById(Long id);
	List<Map<String, String>> relativePath(Category cat);

}