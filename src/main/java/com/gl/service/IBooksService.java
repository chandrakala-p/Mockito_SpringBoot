package com.gl.service;

import java.util.List;

import com.gl.beans.Books;
import com.gl.exception.ProjectException;

public interface IBooksService {

	public Books addBook(Books book);

	public List<Books> getAllBooks();

	public Books getBookById(Integer bookId) throws ProjectException;

	public Books updateBook(Books book) throws ProjectException;

	public String deleteBookById(Integer bookId) throws ProjectException;

	public String addBooksToLike(Integer userId, Integer bookId) throws ProjectException;

	public List<Books> getLikedBooksByUserId(Integer userId) throws ProjectException;

	String deleteBooksFromLike(Integer userId, Integer bookId) throws ProjectException;

}
