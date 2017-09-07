package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
	void editUser(User user);
	void saveUser(User user);
	User userExists(User user);
	
}
