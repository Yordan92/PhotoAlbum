package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import 	org.apache.tomcat.util.http.fileupload.FileUtils;
import com.example.demo.model.Category;
import com.example.demo.model.Picture;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.google.common.collect.Lists;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void addCategory(long parentId, Category cat, User owner) {
		Category persistedCategory = categoryRepository.findOne(parentId);
		if (persistedCategory.getOwner().getId() == owner.getId()) {
			categoryRepository.save(cat);
		} else {
			throw new PermissionDeniedDataAccessException(owner.getUsername() + " has no permissions", null);
		}
	}

	@Override
	public void deleteCategory(long categoryId, User owner) {
		Category persistedCategory = categoryRepository.findOne(categoryId);
		if (persistedCategory.getOwner().getId() == owner.getId()) {
			deleteCategory(categoryId);
		} else {
			throw new PermissionDeniedDataAccessException(owner.getUsername() + " has no permissions", null);
		}
		
	}
	
	private void deleteCategory(long categoryId) {
		Category cat = categoryRepository.findOne(categoryId);
		List<Picture> pictures = cat.getPictures();
		List<Category> children = cat.getChildren();
		if (!pictures.isEmpty()) {
			deletePictures(pictures);
		}
		categoryRepository.delete(cat);
		if (children.isEmpty()) {
			return;
		}
		children.forEach(child -> deleteCategory(child.getId()));
		
	}

	private void deletePictures(List<Picture> pictures) {
		pictures.stream().forEach(picture -> {
			try {
				FileUtils.forceDelete(new File(picture.getPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	@Override
	public void renameCategory(long categoryId, String name, User owner) {
		Category persistedCategory = categoryRepository.findOne(categoryId);
		if (persistedCategory.getOwner().getId() == owner.getId()) {
			categoryRepository.renameCategory(name, categoryId);
		} else {
			throw new PermissionDeniedDataAccessException(owner.getUsername() + " has no permissions", null);
		}
		
	}

	@Override
	public void addPictureToCategory(long categoryId, User owner, Picture picture) {
		Category persistedCategory = categoryRepository.findOne(categoryId);
		if (persistedCategory.getOwner().getId() == owner.getId()) {
			persistedCategory.getPictures().add(picture);
			categoryRepository.save(persistedCategory);
		} else {
			throw new PermissionDeniedDataAccessException(owner.getUsername() + " has no permissions", null);

		}
		
	}
	
}
