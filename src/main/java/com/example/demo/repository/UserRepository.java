package com.example.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.User;


public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findByLastName(String lastName);
	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
}
