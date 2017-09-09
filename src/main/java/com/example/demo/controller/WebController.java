package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Category;
import com.example.demo.model.Picture;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;

@Controller
public class WebController {
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	CategoryService categoryService;
	


	@ResponseBody
	@RequestMapping(value = "/user/signup", method = RequestMethod.POST)
	public void userSignUp(@RequestBody final User user, HttpServletResponse response) throws IOException{
		if (userService.usernameExists(user)) {
			response.sendError(response.SC_NOT_ACCEPTABLE, "User exists with that username");
			return;
		}
		userService.saveUser(user);
		Category rootUserCategory = new Category(null, user.getFirstName(), user);
		categoryService.addCategory(-1, rootUserCategory, user);
		response.setStatus(response.SC_OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/edit", method = RequestMethod.PUT)
	public void userEdit(@RequestBody final User user,  HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		if (session != null) {
			User currentUser = (User) session.getAttribute("currentUser");
			if (currentUser.getId() == user.getId()) {
				userService.saveUser(user);
			}
		}
		response.setStatus(response.SC_OK);
	}
	@ResponseBody
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public User userLogIn(@RequestBody final User postUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userService.findByUsernameAndPassword(postUser);
		if (user == null) {
			response.sendError(response.SC_UNAUTHORIZED, "username or password are incorrect");
			return null;
		} else {
	    	HttpSession newSession = request.getSession();
	    	user.setPassword("");
	    	newSession.setAttribute("currentUser", user);
	    	return user;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/current", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (User) session.getAttribute("currentUser");
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("/index.html");
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/user/addCategory/{parentId}/{name}", method = RequestMethod.GET)
	public void addCategory(@PathVariable(value="parentId") long parentId, @PathVariable(value="name") String newName , HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		User currentUser = (User) session.getAttribute("currentUser");
		Category newCategory = new Category();
		newCategory.setName(newName);
		categoryService.addCategory(parentId, newCategory, currentUser);
		response.setStatus(response.SC_OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/categories/{categoryId}", method = RequestMethod.GET)
	public Category getCategories(@PathVariable(value="categoryId") long categoryId, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		User currentUser = (User) session.getAttribute("currentUser");
		Category result = null;
		if (categoryId == -1) {
			result = categoryService.findByNameAndOwner(currentUser.getFirstName(), currentUser);
			result.setRelativePath(categoryService.relativePath(result));
		} else {
			Category category = categoryService.findById(categoryId);
			if (category.getOwner().getId() == currentUser.getId()) {
				result = category;
				result.setRelativePath(categoryService.relativePath(result));
			}
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/addBinary", consumes=("multipart/form-data"))
	public Map<String, String> acceptData(@RequestParam("file") MultipartFile file, HttpServletRequest requestEntity) throws Exception {
		String store = "images";
		requestEntity.getHeaderNames();
		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		String filename = UUID.randomUUID() + extension;
		
		
		FileUtils.writeByteArrayToFile(new File(store + "/" + filename), file.getBytes());
		Map<String, String> response = new HashMap<>();
		filename=filename.replace('.','@');
		response.put("name", filename);
		return response;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getImage/{path}")
	public byte[] getImage(@PathVariable(value="path") String path, HttpServletResponse response) throws Exception {
		String store = "images";
		path = path.replace('@', '.');
	    return FileUtils.readFileToByteArray(new File(store + "/" + path));
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/users/addImage")
	public void addImage(@RequestBody final Picture picture, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		User currentUser = (User) session.getAttribute("currentUser");
		categoryService.addPictureToCategory(picture.getCategory().getId(),currentUser , picture);
	}

	@RequestMapping("/")
	public String fetchDataByLastName(HttpServletResponse response){
		response.setHeader("Content-Type","text/html");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		return "index.html";
	}
}