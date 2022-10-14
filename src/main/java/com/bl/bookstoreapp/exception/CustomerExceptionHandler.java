package com.bl.bookstoreapp.exception;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class CustomerExceptionHandler {
	@ExceptionHandler(CustomerExceptions.class)
	public ResponseEntity<String> handleCustomerException(CustomerExceptions customerException){
		return new ResponseEntity<>(customerException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgumemtException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return errors;
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String>  handleNoSuchElementException(NoSuchElementException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
