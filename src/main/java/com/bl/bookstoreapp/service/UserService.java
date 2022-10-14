package com.bl.bookstoreapp.service;
import java.io.Console;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bl.bookstoreapp.configuration.RabbitMQConfig;
import com.bl.bookstoreapp.dto.LoginDTO;
import com.bl.bookstoreapp.dto.OrderDto;
import com.bl.bookstoreapp.dto.PasswordDto;
import com.bl.bookstoreapp.dto.ResponseDTO;
import com.bl.bookstoreapp.dto.UserDto;
import com.bl.bookstoreapp.exception.CustomerExceptions;
import com.bl.bookstoreapp.model.Book;
import com.bl.bookstoreapp.model.User;
import com.bl.bookstoreapp.repository.BookRepository;
import com.bl.bookstoreapp.repository.UserRepository;
import com.bl.bookstoreapp.util.EmailSenderService;
import com.bl.bookstoreapp.util.TokenManger;
@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	private EmailSenderService mailService;
	@Autowired
	TokenManger tokenGen;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private AmqpTemplate template;
	@Override
	public void send(String token) {
		String email = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		template.convertAndSend(RabbitMQConfig.exchange, RabbitMQConfig.routingkey, email);
//		System.out.println("Send msg = " + user.getCartBook());
	}
	// Ability to serve controller's insert user record api call
	public User registerUser(UserDto userdto) {
		User user = convertCusDtoToEntity(userdto);
		Optional<User> newUser = userRepo.findByEmail(user.getEmail());
		if (newUser.isPresent()) {
			throw new CustomerExceptions("Customer is already present");
		}
//			mailService.sendEmail(user.getEmail());
		return userRepo.save(user);
	}
	// Ability to serve controller's user login api call
	@Override
	public ResponseDTO userLogin(LoginDTO logindto)  {
		Optional<User> user = userRepo.findByEmail(logindto.getEmail());
		if (logindto.getEmail().equals(user.get().getEmail())
				&& logindto.getPassword().equals(user.get().getPassword())) {
			String token = tokenGen.createToken(user.get().getEmail());
			mailService.sendEmail(user.get().getEmail(),"user logged successfully");
			return new ResponseDTO(token, user.get().getEmail());
		} else {
			throw new CustomerExceptions("User doesn't exists");
		}
	}
	@Override
	public UserDto convertEntityToDto(@Valid User user) {
		UserDto userDto = new UserDto();
		userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
	@Override
	public ResponseDTO addBookToCart(int bookID, String token) {
		String email = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		Book book = bookRepo.findById(bookID).get();
		book.setAddedToBag(true);
		book.setQuantity(book.getQuantity() - 1);
		user.getCartBook().add(book);
		user = userRepo.save(user);
		return new ResponseDTO("Book Added in the cart", user);
	}
	public OrderDto placeOrder(String token) {
		String email = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		double totalCost = user.getCartBook().stream().
				map((order) -> order.getPrice()).reduce(0.0,Double::sum);
		OrderDto orderDto = new OrderDto();
		orderDto.setTotalCost(totalCost);
		orderDto.setCartBook(user.getCartBook());
		userRepo.save(user);
		mailService.sendEmail(user.getEmail(),"Thank you ordering");
		return orderDto;
	}
	public User convertCusDtoToEntity(UserDto userdto) {
		User user = new User();
		user = modelMapper.map(userdto, User.class);
		return user;
	}
	@Override
	public List<Book> getCartDetails(String token) {
		String emailId = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(emailId).orElseThrow(() -> new CustomerExceptions("User Not Found"));
//		Book book = bookRepo.findById(bookID).get();
//		user.getCartBook().remove(book);
//		book.setAddedToBag(false);
		return user.getCartBook();
	}
	@Override
	public String removeFromCart(int bookID, String token) {
		String emailId = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(emailId).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		Book book = bookRepo.findById(bookID).get();
		user.getCartBook().remove(book);
		book.setAddedToBag(false);
		userRepo.save(user);
		return "Book removed from cart";
	}
	public ResponseDTO getToken(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		String token = tokenGen.createToken(user.get().getEmail());
		mailService.sendEmail(user.get().getEmail(),token);
		//mailService.sendEmail("ravirenapurkar@gmail.com","Welcome "+user.get().getFirstName(),"Token for changing password is :\n"+token);
//		log.info("Token sent on mail successfully");
		return new ResponseDTO(token, user.get().getEmail());
	}
	@Override
	public User changePassword(@Valid PasswordDto passwordDTO,String token) {
		String emailId = tokenGen.decodeToken(token);
//		String generatedToken = userService.getToken(email);
		User user = userRepo.findByEmail(emailId).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		
				if(passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword()) ) {
				user.setPassword(passwordDTO.getNewPassword());
				userRepo.save(user);
				}else {
					throw new CustomerExceptions("Invalid Password");
				}
				return user;
	}
	@Override
	public void validateToken(String token) {
		String emailId = tokenGen.decodeToken(token);
		User user = userRepo.findByEmail(emailId).orElseThrow(() -> new CustomerExceptions("User Not Found"));
		
	}
	

}
