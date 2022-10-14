package com.bl.bookstoreapp.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bl.bookstoreapp.model.Book;
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query(value="select * from book ORDER BY price DESC",nativeQuery = true)
	public List<Book> sortBooksDesc();
//	public List<Book> findByBookName(String bookName);
//	public Book getByBookName(String bookName);
	public Optional<Book> findByBookName(String bookName);
	public Object save(Optional<Book> book);
//	public void delete(String bookName);
}
