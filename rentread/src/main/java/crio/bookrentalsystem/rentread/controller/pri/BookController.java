package crio.bookrentalsystem.rentread.controller.pri;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crio.bookrentalsystem.rentread.dto.BookRegisterRequest;
import crio.bookrentalsystem.rentread.dto.RentResponse;
import crio.bookrentalsystem.rentread.dto.StatusMessage;
import crio.bookrentalsystem.rentread.model.Book;
import crio.bookrentalsystem.rentread.service.pri.BookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




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

    @PutMapping("update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookRegisterRequest bookRegisterRequest) {
        //TODO: process PUT request

        String bookTitle = bookRegisterRequest.getBookTitle();
        String bookAuthor = bookRegisterRequest.getBookAuthor();
        String bookStatus = bookRegisterRequest.getBookStatus();
        String bookGenre = bookRegisterRequest.getBookGenre();

        Book book = bookService.updateBookS(id, bookTitle, bookAuthor, bookGenre, bookStatus);
        
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable int id) {
        //TODO: process PUT request

        boolean resp = bookService.deleteBookS(id);
        
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {

        List<Book> books = bookService.getAvailableBookS();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/rent")
    public ResponseEntity<RentResponse> rentBook(@RequestHeader(value = "bookId") int bookId, @RequestHeader(value = "username", defaultValue = "") String username) {

        RentResponse rentResponse  = bookService.rentBookS(bookId, username);
        
        return new ResponseEntity<>(rentResponse, HttpStatus.CREATED);
    }

    @PostMapping("/unrent")
    public ResponseEntity<StatusMessage> unrentBook(@RequestHeader(value = "bookId") int bookId, @RequestHeader(value = "username", defaultValue = "") String username) {

        StatusMessage resp  = bookService.unrentBookS(bookId, username);
        
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {

        List<Book> books = bookService.getAllBookS();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    
}
