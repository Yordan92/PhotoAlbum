package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Picture;
import com.example.demo.model.User;

public interface CategoryService {

	void renameCategory(long category_id, String name, User owner);
	void deleteCategory(long category_id, User owner);
	void addPictureToCategory(long category_id, User owner, Picture picture);
	void addCategory(long parentId, Category cat, User owner);

}