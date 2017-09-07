package com.example.demo;


import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoAlbumApplicationTests {
	
	@Autowired
	UserRepository  repository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Before
	public void initDBContent() {
		User testUser = new User("Test", "Testov", "testovi", "t@t", "t@t");
		repository.save(testUser);
		Category cat = new Category(null, "root",testUser);
		Category cat1_1 = new Category(cat, "cat1_1",testUser);
		Category cat1_2 = new Category(cat, "cat1_2",testUser);
		Category cat2_1 = new Category(cat1_2, "cat2_1",testUser);
		categoryRepository.save(Arrays.asList(cat, cat1_1, cat1_2, cat2_1));
//		categoryRepository.save(Arrays.asList(cat, cat1_2, cat2_1));

		
	}
	
//	@Test
//	public void imutableUsernameCheck() {
//		User testUser = repository.findByUsername("testovi");
//		String oldUsername = testUser.getUsername(); 
//		testUser.setUsername("aaaa");
//		testUser.setEmail("changed");
//		repository.save(testUser);
//		User afterUpdateUser = repository.findByUsername("testovi");
//		assertNotNull(afterUpdateUser);
//		assertEquals("user was updated", afterUpdateUser.getEmail(), "changed");
//		assertEquals("should be equal!!!", oldUsername, afterUpdateUser.getUsername());
//		
//	}
	
	@Test
	public void checkCategories() {
		Category cat = categoryRepository.findByName("root");
		System.out.println(cat.getChildren());
	}
	
	@After
	public void deleteData() {
		User[] testUser = { repository.findByUsername("testovi"), repository.findByUsername("aaaa") } ;
		
		Arrays.stream(testUser)
			.filter(user -> user != null)
			.forEach(user -> {
				repository.delete(user);
			});
//		
	}

}
