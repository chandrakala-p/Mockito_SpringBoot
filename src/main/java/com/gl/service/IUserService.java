package com.gl.service;

import java.util.List;

import com.gl.beans.User;
import com.gl.exception.ProjectException;

public interface IUserService {

	public User addUser(User user);

	public List<User> getAllUsers();

	public User getUserById(Integer userId) throws ProjectException;

	public User updateUser(User user) throws ProjectException;

	public String deleteUserById(Integer userId) throws ProjectException;

}
