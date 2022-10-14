package com.bl.bookstoreapp.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.bl.bookstoreapp.dto.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
//Map to a database table
@Entity
//Use to bundle features of getter and setter
@Data
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer bookID;
  private String bookName;
  private String authorName;
  private String bookDescription;
  private String bookImg;
  private Double price;
  private Integer quantity;
  private boolean addedToBag;
  
  public Book(BookDTO dto) {
		super();
		this.bookName = dto.getBookName();
		this.authorName = dto.getAuthorName();
		this.bookDescription = dto.getBookDescription();
		this.bookImg = dto.getBookImg();
		this.price = dto.getPrice();
		this.quantity = dto.getQuantity();
		this.addedToBag = dto.isAddedToBag();
	}
	public Book(String bookName, BookDTO dto) {
		super();
		this.bookID = bookID;
		this.bookName = dto.getBookName();
		this.authorName = dto.getAuthorName();
		this.bookDescription = dto.getBookDescription();
		this.bookImg = dto.getBookImg();
		this.price = dto.getPrice();
		this.quantity = dto.getQuantity();
		this.addedToBag = dto.isAddedToBag();
	}
	public Book() {
		super();
	}
	public Book(Integer bookID, String bookName, String authorName, String bookDescription, String bookImg,
			Double price, Integer quantity, boolean addedToBag) {
		
		this.bookID = bookID;
		this.bookName = bookName;
		this.authorName = authorName;
		this.bookDescription = bookDescription;
		this.bookImg = bookImg;
		this.price = price;
		this.quantity = quantity;
		this.addedToBag = addedToBag;
	}
}
