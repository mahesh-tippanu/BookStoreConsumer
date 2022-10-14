package com.bl.bookstoreapp.service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.bl.bookstoreapp.dto.BookDTO;
import com.bl.bookstoreapp.exception.CustomerExceptions;
import com.bl.bookstoreapp.model.Book;
import com.bl.bookstoreapp.model.User;
import com.bl.bookstoreapp.repository.BookRepository;
import com.bl.bookstoreapp.repository.UserRepository;
import com.bl.bookstoreapp.util.TokenManger;
@Service
public class BookService implements IBookService {
	// Autowired BookRepository to inject its dependency here
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private TokenManger tokenGen;
	@Autowired
	private UserRepository userRepo;
	@Override
	public BookDTO convertEntityToDto(@Valid Book book) {
		BookDTO bookDto = new BookDTO();
		bookDto = modelMapper.map(book, BookDTO.class);
		return bookDto;
	}
	@Override
	public Book insertBook(@Valid Book book) {
		return bookRepo.save(book);
	}
	@Override
	public List<Book> getAllBookRecords() {
		List<Book> bookList = bookRepo.findAll();
		return bookList;
	}
	@Override
	public Book getRecordByBookName(String bookName) {
		Book book = bookRepo.findByBookName(bookName).get();
		bookRepo.findByBookName(bookName).orElseThrow(() -> new CustomerExceptions("Book Not Found"));
			return book;
		
	}
	@Override
	public String deleteBookRecord(String bookName) {
		Book book = bookRepo.findByBookName(bookName).get();
		bookRepo.findByBookName(bookName).orElseThrow(() -> new CustomerExceptions("Book Not Found"));
			bookRepo.deleteById(book.getBookID());
			return "Book is deleted";
	}
	@Override
	public List<Book> sortRecordDesc() {
//		List<Book> listOfBooks = bookRepo.sortBooksDesc();
//		return listOfBooks;
		return bookRepo.findAll(Sort.by(Sort.Direction.DESC, "price"));
	}
	@Override
	public List<Book> sortRecordAsc(String token) {
		String email = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		return bookRepo.findAll(Sort.by(Sort.Direction.ASC, "price"));
	}
	@Override
	public Book updateBookRecord(String bookName, @Valid BookDTO bookdto) {
		Book book = bookRepo.findByBookName(bookName).get();
		if (book != null) {
			throw new CustomerExceptions("Book Record doesn't exists");
		} else {
			Book newBook = new Book(bookName, bookdto);
			bookRepo.save(newBook);
			return newBook;
		}
	}
	@Override
	public List<Book> sortByBookNameHTL() {
//		List<Book> book = books.stream().sorted(Comparator.comparingDouble(Book::getPrice).reversed()).
//				 collect(Collectors.toList());
		List<Book> books = bookRepo.findAll().stream()
				.sorted(Comparator.comparing(Book::getBookName, String.CASE_INSENSITIVE_ORDER).reversed())
				.collect(Collectors.toList());
//		return books;
//		List<Book> books = bookRepo.findAll(Sort.by(Sort.Direction.ASC, "bookName"));
		if (books.isEmpty()) {
			throw new CustomerExceptions("Book Record doesn't exists");
		}
		return books;
	}
	@Override
	public List<Book> sortByBookNameLTH() {
//		List<Book> books = bookRepo.findAll(Sort.by(Sort.Direction.DESC, "bookName"));
		List<Book> books = bookRepo.findAll().stream()
				.sorted(Comparator.comparing(Book::getBookName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
		if (books.isEmpty()) {
			throw new CustomerExceptions("Book Record doesn't exists");
		}
//		return convertAllEntityToDto(books);
		return books;
	}
	public List<Book> sortByNewestArrival() {
		List<Book> books = bookRepo.findAll().stream().collect(Collectors.collectingAndThen(Collectors.toList(), l -> {
			Collections.reverse(l);
			return l;
		}));
		if (books.isEmpty()) {
			throw new CustomerExceptions("No book details to show");
		}
		return books;
	}
}
