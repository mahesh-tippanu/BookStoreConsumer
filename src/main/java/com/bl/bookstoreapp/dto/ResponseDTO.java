package com.bl.bookstoreapp.dto;
import lombok.Data;
@Data
public class ResponseDTO {
	private String message;
	private Object data;
	public ResponseDTO(String message) {
		super();
		this.message = message;
	}
	public ResponseDTO(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}
}
