package com.bl.bookstoreapp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bl.bookstoreapp.dto.LoginDTO;
import com.bl.bookstoreapp.dto.OrderDto;
import com.bl.bookstoreapp.dto.PasswordDto;
import com.bl.bookstoreapp.dto.ResponseDTO;
import com.bl.bookstoreapp.dto.UserDto;
import com.bl.bookstoreapp.model.User;
import com.bl.bookstoreapp.service.IUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userregistration")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	@Autowired
	IUserService userService;

	@PostMapping("/register")
	@ApiOperation("Register the new user")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user) {
		User newUser = userService.registerUser(user);
		UserDto Dto = userService.convertEntityToDto(newUser);
		return ResponseEntity.status(200).body("employee registered sucessfully");
	}

	// Ability to call api to login user
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody LoginDTO logindto) {
		ResponseDTO user = userService.userLogin(logindto);
		ResponseDTO dto = new ResponseDTO("User logged in successfully !");
		return new ResponseEntity<ResponseDTO>(user, HttpStatus.OK);
	}
	
	@PutMapping("/getToken/{email}")
	public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
		ResponseDTO generatedToken = userService.getToken(email);
		ResponseDTO dto = new ResponseDTO("Token for mail id sent on mail successfully !",generatedToken);
		return new ResponseEntity<ResponseDTO>(generatedToken,HttpStatus.OK);
	}
	@PostMapping("/tokenvalidation/{token}")
	public ResponseEntity verifyToken(@Valid @RequestHeader @PathVariable String token) {
		userService.validateToken(token);
		return new ResponseEntity(HttpStatus.OK);
			
	}
	
	@PostMapping("/changepassword")
	public ResponseEntity<ResponseDTO> forgotPassword(@Valid  @RequestBody PasswordDto passwordDTO, @RequestHeader String token  ){
		User newUser = userService.changePassword(passwordDTO,token);
		ResponseDTO dto = new ResponseDTO("Password Resetted successfully !",newUser);
		return new ResponseEntity(dto,HttpStatus.OK);
	}

	@PostMapping("/book/cart/{bookID}")
	public ResponseEntity<ResponseDTO> addBookToCart(@PathVariable int bookID, @RequestHeader String token) {
		return new ResponseEntity<ResponseDTO>(userService.addBookToCart(bookID, token), HttpStatus.OK);
	}

	@PutMapping("/book/order")
	public ResponseEntity<OrderDto> placeOrder(@RequestHeader String token) {
		return new ResponseEntity(userService.placeOrder(token), HttpStatus.OK);
	}

	@GetMapping("/book/ordermessage")
	public String producer(@RequestHeader String token) {
		userService.send(token);
		return "JMS Message sent to the RabbitMQ Successfully";
	}
	@PutMapping("/book/removefromcart/{bookId}")
	public ResponseEntity<String> removeItemFromCart(@PathVariable int bookId,@RequestHeader String token){
		return new ResponseEntity<String>(userService.removeFromCart(bookId, token),HttpStatus.OK);
	}
	
	@GetMapping("/book/getcartdetails")
	public ResponseEntity<User> getCartDetails(@RequestHeader String token){
		return new ResponseEntity(userService.getCartDetails(token),HttpStatus.OK);
	}
}
