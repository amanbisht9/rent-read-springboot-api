package crio.bookrentalsystem.rentread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crio.bookrentalsystem.rentread.model.Book;

public interface IBookRepositary extends JpaRepository<Book, Integer>{
    
}
