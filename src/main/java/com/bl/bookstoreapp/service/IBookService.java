package com.bl.bookstoreapp.service;

import java.util.List;

import javax.validation.Valid;

import com.bl.bookstoreapp.dto.BookDTO;
import com.bl.bookstoreapp.model.Book;

public interface IBookService {
	public Book insertBook(Book book);
	public List<Book> getAllBookRecords();
	public Book getRecordByBookName(String bookName);
	public String deleteBookRecord(String bookName);
	public List<Book> sortRecordDesc();
	public List<Book> sortRecordAsc(String token);
	public BookDTO convertEntityToDto(Book newBook);
	public Book updateBookRecord(String bookName, @Valid BookDTO bookdto);
	public List<Book> sortByBookNameLTH();
	List<Book> sortByBookNameHTL();
	public List<Book> sortByNewestArrival();



	


}
