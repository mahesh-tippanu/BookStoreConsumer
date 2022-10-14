package com.bl.bookstoreapp.dto;
import lombok.Data;
//Data transfer object to login user
@Data
public class LoginDTO {
	private String email;
	private String password;
}
