package com.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.beans.User;
import com.gl.dao.IUserRepository;
import com.gl.exception.ProjectException;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer userId) throws ProjectException {
		// TODO Auto-generated method stub
		return userRepository.findById(userId).orElseThrow(() -> new ProjectException("User Id not found"));
	}

	@Override
	public User updateUser(User user) throws ProjectException {
		// TODO Auto-generated method stub
		userRepository.findById(user.getId()).orElseThrow(() -> new ProjectException("User Id not found"));
		return userRepository.saveAndFlush(user);
	}

	@Override
	public String deleteUserById(Integer userId) throws ProjectException {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException("User Id not found"));
		userRepository.delete(user);
		return "Deleted Successfully";
	}

}
