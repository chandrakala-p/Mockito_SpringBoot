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

import com.gl.beans.Books;
import com.gl.beans.User;
import com.gl.dao.IBooksRepository;
import com.gl.dao.IUserRepository;
import com.gl.exception.ProjectException;

@SpringBootTest
class BooksServiceImplTest {

	@InjectMocks
	BooksServiceImpl service;

	@Mock
	IBooksRepository bookDao;

	@Mock
	IUserRepository userDao;

	@Test
	public void addBooksTest() {
		Books book = new Books();
		service.addBook(book);
		verify(bookDao, times(1)).save(book);
	}

	@Test
	public void getAllBooksTest() {
		List<Books> list = new ArrayList<Books>();
		Books book1 = new Books();
		Books book2 = new Books();
		Books book3 = new Books();
		list.add(book1);
		list.add(book2);
		list.add(book3);
		when(bookDao.findAll()).thenReturn(list);
		List<Books> bookList = service.getAllBooks();
		assertEquals(3, bookList.size());
		verify(bookDao, times(1)).findAll();
	}

	@Test
	public void getBooksByIdTest() throws ProjectException {
		Books book = new Books(1, "book1", "genre1", null);
		when(bookDao.findById(1)).thenReturn(Optional.of(book));
		Books book1 = service.getBookById(1);
		assertEquals("book1", book1.getName());
		assertEquals("genre1", book1.getGenre());

		// For failure
		Exception e = assertThrows(ProjectException.class, () -> {
			service.getBookById(2);
		});
		assertEquals("Book Id not found", e.getMessage());
	}

	@Test
	public void updateBookTest() throws ProjectException {
		Books book = new Books(1, "book1", "genre", null);
		when(bookDao.findById(book.getId())).thenReturn(Optional.of(book));
		service.updateBook(book);
		verify(bookDao, times(1)).saveAndFlush(book);

		// For failure
		Books book1 = new Books();
		Exception e = assertThrows(ProjectException.class, () -> {
			service.updateBook(book1);
		});
		assertEquals("Book Id not found", e.getMessage());
	}

	@Test
	public void deleteBookByIdTest() throws ProjectException {
		Books book = new Books(1, "book1", "genre", null);
		when(bookDao.findById(book.getId())).thenReturn(Optional.of(book));
		assertEquals("Deleted Successfully", service.deleteBookById(1));
		verify(bookDao, times(1)).delete(book);

		// For failure
		Exception e = assertThrows(ProjectException.class, () -> {
			service.deleteBookById(2);
		});
		assertEquals("Book Id not found", e.getMessage());
	}

	@Test
	public void addBooksToLikeTest() throws ProjectException {
		List<Books> bookList = new ArrayList<Books>();
		Books book = new Books(1, "book1", "genre1", null);
		when(bookDao.findById(book.getId())).thenReturn(Optional.of(book));
		User user = new User(1, "chandrakala P", "chandrakala P", bookList);
		when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
		List<Books> likedBooksList = user.getLikedBooks();
		likedBooksList.add(book);
		user.setLikedBooks(likedBooksList);
		userDao.save(user);
		assertEquals("Added To Like", service.addBooksToLike(1, 1));
	}

	@Test
	public void getLikedBooksByUserIdTest() throws ProjectException {
		List<Books> bookList = new ArrayList<Books>();
		Books book1 = new Books(1, "book1", "genre1", null);
		Books book2 = new Books(1, "book2", "genre2", null);
		bookList.add(book1);
		bookList.add(book2);
		User user = new User(1, "chandrakala P", "password", bookList);
		when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
		List<Books> bookList1 = user.getLikedBooks();
		assertEquals("book1", bookList1.get(0).getName());
		assertEquals("genre1", bookList1.get(0).getGenre());
		assertEquals("book2", bookList1.get(1).getName());
		assertEquals("genre2", bookList1.get(1).getGenre());
	}

	@Test
	public void deleteBooksFromLikeTest() throws ProjectException {
		List<Books> bookList = new ArrayList<Books>();
		Books book = new Books(1, "book1", "genre1", null);
		when(bookDao.findById(book.getId())).thenReturn(Optional.of(book));
		User user = new User(1, "chandrakala p", "password", bookList);
		when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
		List<Books> likedBooksList = user.getLikedBooks();
		likedBooksList.removeIf(n -> (n.getId() == book.getId()));
		user.setLikedBooks(likedBooksList);
		userDao.save(user);
		assertEquals("Deleted From Like", service.deleteBooksFromLike(1, 1));

	}

}
