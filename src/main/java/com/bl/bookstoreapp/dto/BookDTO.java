package com.bl.bookstoreapp.dto;
import lombok.Data;
@Data
public class BookDTO {
	private String bookName;
	private String authorName;
	private String bookDescription;
	private String bookImg;
	private Double price;
	private Integer quantity;
	private boolean addedToBag;
}
