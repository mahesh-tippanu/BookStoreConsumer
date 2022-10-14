package com.bl.bookstoreapp.dto;

public class PasswordDto {
	

	private String newPassword;
	private String confirmPassword;
	
	
	public PasswordDto() {
		super();
		
	}

	public PasswordDto( String newPassword, String confirmPassword) {
		super();
		
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
		
	}



	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
}
