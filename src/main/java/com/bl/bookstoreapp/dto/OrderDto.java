package com.bl.bookstoreapp.dto;
import java.util.LinkedList;
import java.util.List;

import com.bl.bookstoreapp.model.Book;
import lombok.Data;

@Data
public class OrderDto {

	private List<Book> cartBook = new LinkedList<>();
	private double totalCost;
}
