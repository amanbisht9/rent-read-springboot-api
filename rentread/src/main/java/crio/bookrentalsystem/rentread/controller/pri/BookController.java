package crio.bookrentalsystem.rentread.controller.pri;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crio.bookrentalsystem.rentread.dto.BookRegisterRequest;
import crio.bookrentalsystem.rentread.model.Book;
import crio.bookrentalsystem.rentread.service.pri.BookService;
import crio.bookrentalsystem.rentread.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @PostMapping("/register")
    public ResponseEntity<Book> registerBook(@RequestBody BookRegisterRequest bookRegisterRequest) {
        
        String bookTitle = bookRegisterRequest.getBookTitle();
        String bookAuthor = bookRegisterRequest.getBookAuthor();
        String bookStatus = bookRegisterRequest.getBookStatus();
        String bookGenre = bookRegisterRequest.getBookGenre();

        Book book = bookService.registerBookS(bookTitle, bookAuthor, bookGenre, bookStatus);
        
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    
}
