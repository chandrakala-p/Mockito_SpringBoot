package com.gl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.beans.ResponseMessage;
import com.gl.beans.User;
import com.gl.exception.ProjectException;
import com.gl.service.IUserService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {

	@Autowired
	IUserService userService;

	// Register User
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {

		return new ResponseEntity<User>(userService.addUser(user), HttpStatus.CREATED);
	}

	// Get All Users
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {

		return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
	}

	// Get User By ID
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) throws ProjectException {

		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	// Update User
    
	@RequestMapping(method = RequestMethod.PUT, value = "/users/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws ProjectException {

		return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
	}

	// Delete User
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
	public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable("id") int id) throws ProjectException {
		ResponseMessage rm = new ResponseMessage();
		rm.setMessage(userService.deleteUserById(id));
		return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);
	}

	@ExceptionHandler(ProjectException.class)
	public ResponseEntity<ResponseMessage> handleEmployeeIdException(HttpServletRequest request, Exception ex) {
		ResponseMessage rm = new ResponseMessage();
		rm.setMessage(ex.getMessage());
		rm.setErrorCode(404);
		return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NOT_FOUND);
	}

}
