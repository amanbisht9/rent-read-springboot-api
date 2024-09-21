package crio.bookrentalsystem.rentread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crio.bookrentalsystem.rentread.model.Book;
import java.util.List;


public interface IBookRepositary extends JpaRepository<Book, Integer>{
    List<Book> findByBookStatus(String bookStatus);
    
}
