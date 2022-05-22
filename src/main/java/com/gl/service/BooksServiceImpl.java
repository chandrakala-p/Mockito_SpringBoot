package com.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.beans.Books;
import com.gl.beans.User;
import com.gl.dao.IBooksRepository;
import com.gl.dao.IUserRepository;

import com.gl.exception.ProjectException;

@Service
public class BooksServiceImpl implements IBooksService {

	@Autowired
	IBooksRepository booksRepository;
	@Autowired
	IUserRepository userRepository;

	@Override
	public Books addBook(Books book) {
		// TODO Auto-generated method stub
		return booksRepository.save(book);
	}

	@Override
	public List<Books> getAllBooks() {
		// TODO Auto-generated method stub
		return booksRepository.findAll();
	}

	@Override
	public Books getBookById(Integer bookId) throws ProjectException {
		// TODO Auto-generated method stub
		return booksRepository.findById(bookId).orElseThrow(() -> new ProjectException("Book Id not found"));
	}

	@Override
	public Books updateBook(Books book) throws ProjectException {
		// TODO Auto-generated method stub
		booksRepository.findById(book.getId())
				.orElseThrow(() -> new ProjectException("Book Id not found"));
		return booksRepository.saveAndFlush(book);
	}
	
	

	@Override
	public String deleteBookById(Integer bookId) throws ProjectException {
		// TODO Auto-generated method stub
		Books book = booksRepository.findById(bookId).orElseThrow(() -> new ProjectException("Book Id not found"));
		booksRepository.delete(book);
		return "Deleted Successfully";
	}

	@Override
	public String addBooksToLike(Integer userId, Integer bookId) throws ProjectException {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException("User Id not found"));
		Books book = booksRepository.findById(bookId).orElseThrow(() -> new ProjectException("Book Id not found"));
		List<Books> likedBooksList = user.getLikedBooks();
		likedBooksList.add(book);
		user.setLikedBooks(likedBooksList);
		userRepository.save(user);
		return "Added To Like";
	}

	@Override
	public String deleteBooksFromLike(Integer userId, Integer bookId) throws ProjectException {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException("User Id not found"));
		Books book = booksRepository.findById(bookId).orElseThrow(() -> new ProjectException("Book Id not found"));
		List<Books> likedBooksList = user.getLikedBooks();
		likedBooksList.removeIf(n -> (n.getId() == book.getId()));
		user.setLikedBooks(likedBooksList);
		userRepository.save(user);
		return "Deleted From Like";
	}

	@Override
	public List<Books> getLikedBooksByUserId(Integer userId) throws ProjectException {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException("User Id not found"));
		return user.getLikedBooks();
	}

}
