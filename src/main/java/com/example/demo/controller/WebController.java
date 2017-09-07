package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class WebController {
	@Autowired
	UserRepository repository;
	
	@RequestMapping("/save")
	public String process(){
		repository.save(new User("Jack", "Smith"));
		repository.save(new User("Adam", "Johnson"));
		repository.save(new User("Kim", "Smith"));
		repository.save(new User("David", "Williams"));
		repository.save(new User("Peter", "Davis"));
		return "Done";
	}
	
	
	@RequestMapping("/findall")
	public String findAll(){
		String result = "<html>";
		
		for(User cust : repository.findAll()){
			result += "<div>" + cust.toString() + "</div>";
		}
		
		return result + "</html>";
	}
	
	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") long id){
		String result = "";
		result = repository.findOne(id).toString();
		return result;
	}
	
	@RequestMapping("/")
	public String fetchDataByLastName(HttpServletResponse response){
		response.setHeader("Content-Type","text/html");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		return "index1.html";
	}
}