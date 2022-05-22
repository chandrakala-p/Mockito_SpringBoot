package com.gl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.gl.beans.User;
import com.gl.dao.IUserRepository;
import com.gl.exception.ProjectException;

@SpringBootTest
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl service;

	@Mock
	IUserRepository dao;

	@Test
	public void addUserTest() {
		User user = new User();
		service.addUser(user);
		verify(dao, times(1)).save(user);
	}

	@Test
	public void getAllUsersTest() {
		List<User> list = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		when(dao.findAll()).thenReturn(list);
		List<User> userList = service.getAllUsers();
		assertEquals(3, userList.size());
		verify(dao, times(1)).findAll();
	}

	@Test
	public void getUserByIdTest() throws ProjectException {
		User user = new User(1, "chandrakala P", "password", null);
		when(dao.findById(1)).thenReturn(Optional.of(user));
		User user1 = service.getUserById(1);
		assertEquals("chandrakala P", user1.getUserName());
		assertEquals("password", user1.getPassword());

		// For failure
		Exception e = assertThrows(ProjectException.class, () -> {
			service.getUserById(2);
		});
		assertEquals("User Id not found", e.getMessage());
	}

	@Test
	public void updateUserTest() throws ProjectException {
		User user = new User(1, "chandrakala P", "password", null);
		when(dao.findById(user.getId())).thenReturn(Optional.of(user));
		service.updateUser(user);
		verify(dao, times(1)).saveAndFlush(user);

		// For failure
		User user1 = new User();
		Exception e = assertThrows(ProjectException.class, () -> {
			service.updateUser(user1);
		});
		assertEquals("User Id not found", e.getMessage());
	}

	@Test
	public void deleteUserByIdTest() throws ProjectException {
		User user = new User(1, "chandrakala P", "password", null);
		when(dao.findById(user.getId())).thenReturn(Optional.of(user));
		assertEquals("Deleted Successfully", service.deleteUserById(1));
		verify(dao, times(1)).delete(user);

		// For failure
		Exception e = assertThrows(ProjectException.class, () -> {
			service.deleteUserById(2);
		});
		assertEquals("User Id not found", e.getMessage());
	}

}
