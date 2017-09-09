package com.example.demo.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private User updateUserPassword(User user) {
		try {
			user.setPassword(Utils.SHAsum(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public void editUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUser(User user) {
		user = updateUserPassword(user);
		userRepository.save(user);
		
	}

	@Override
	public User findByUsernameAndPassword(User user) {
		user = updateUserPassword(user);
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
	@Override
	public boolean usernameExists(User user) {
		return userRepository.findByUsername(user.getUsername()) != null;
	}

}
