package com.bl.bookstoreapp.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;
@Data
public class UserDto {
//	@NotEmpty(message = "Name cant be empty")
//	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "User Name is Invalid")
	private String name;
	private long phoneNumber;
//	@Email
	private String email;
//	@NotEmpty(message = "Address can not be empty")
	private String address;
//	@NotEmpty(message = "City can not be empty")
	private String city;
	private int pincode;
	private String landmark;
//	@NotEmpty(message = "Password can not be empty")
	private String password;
}
