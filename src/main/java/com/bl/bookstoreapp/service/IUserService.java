package com.bl.bookstoreapp.service;

import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import com.bl.bookstoreapp.dto.LoginDTO;
import com.bl.bookstoreapp.dto.OrderDto;
import com.bl.bookstoreapp.dto.PasswordDto;
import com.bl.bookstoreapp.dto.ResponseDTO;
import com.bl.bookstoreapp.dto.UserDto;
import com.bl.bookstoreapp.model.Book;
import com.bl.bookstoreapp.model.User;
public interface IUserService {
//	UserDto convertEntityToDto(@Valid LoginDTO userRegistration);
	public ResponseDTO userLogin(LoginDTO logindto) ;
	UserDto convertEntityToDto(@Valid User user);
	public User registerUser(@Valid UserDto userDto);
	public ResponseDTO addBookToCart(int bookID, String token);
	public Object placeOrder(String token);
	public void send(String token);
	public List<Book> getCartDetails(String token);
	public String removeFromCart(int bookId, String token);
	public ResponseDTO getToken(String email);
	public void validateToken(String token);
	public User changePassword(@Valid PasswordDto passwordDTO, String token);
}
