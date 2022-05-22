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

import com.gl.beans.Books;
import com.gl.beans.ResponseMessage;
import com.gl.exception.ProjectException;
import com.gl.service.IBooksService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class BooksController {

	@Autowired
	IBooksService booksService;

	// Add Books
	@RequestMapping(method = RequestMethod.POST, value = "/books")
	public ResponseEntity<Books> addBook(@RequestBody Books book) {

		return new ResponseEntity<Books>(booksService.addBook(book), HttpStatus.CREATED);
	}

	// Get All Books)
	@RequestMapping(method = RequestMethod.GET, value = "/books")
	public ResponseEntity<List<Books>> getAllBooks() {

		return new ResponseEntity<List<Books>>(booksService.getAllBooks(), HttpStatus.OK);
	}

	// Get Book By ID
	@RequestMapping(method = RequestMethod.GET, value = "/books/{id}")
	public ResponseEntity<Books> getBookById(@PathVariable("id") Integer id) throws ProjectException {

		return new ResponseEntity<Books>(booksService.getBookById(id), HttpStatus.OK);
	}

	// Update Books
	@RequestMapping(method = RequestMethod.PUT, value = "/books")
	public ResponseEntity<Books> updateBook(@RequestBody Books book) throws ProjectException {

		return new ResponseEntity<Books>(booksService.updateBook(book), HttpStatus.OK);
	}

	// Delete Book By Id
	@RequestMapping(method = RequestMethod.DELETE, value = "/books/{id}")
	public ResponseEntity<ResponseMessage> deleteBookById(@PathVariable("id") int id) throws ProjectException {
		ResponseMessage rm = new ResponseMessage();
		rm.setMessage(booksService.deleteBookById(id));
		return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);
	}

	// Add Books To Like
	@RequestMapping(method = RequestMethod.POST, value = "/addBooksToLike/{userId}/{bookId}")
	public ResponseEntity<String> addBooksToLike(@PathVariable int userId, @PathVariable int bookId)
			throws ProjectException {
		return new ResponseEntity<String>(booksService.addBooksToLike(userId, bookId), HttpStatus.OK);
	}

	// Delete Books From Like
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteBooksFromLike/{userId}/{bookId}")
	public ResponseEntity<String> deleteBooksFromLike(@PathVariable int userId, @PathVariable int bookId)
			throws ProjectException {
		return new ResponseEntity<>(booksService.deleteBooksFromLike(userId, bookId), HttpStatus.OK);
	}
	
	
	

	@ExceptionHandler(ProjectException.class)
	public ResponseEntity<ResponseMessage> handleEmployeeIdException(HttpServletRequest request, Exception ex) {
		ResponseMessage rm = new ResponseMessage();
		rm.setMessage(ex.getMessage());
		rm.setErrorCode(404);
		return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NOT_FOUND);
	}

}
