package crio.bookrentalsystem.rentread.service.pri;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.dto.BookStatus;
import crio.bookrentalsystem.rentread.dto.RentResponse;
import crio.bookrentalsystem.rentread.dto.StatusMessage;
import crio.bookrentalsystem.rentread.exception.FieldException;
import crio.bookrentalsystem.rentread.exception.LoginsException;
import crio.bookrentalsystem.rentread.exception.NotFoundException;
import crio.bookrentalsystem.rentread.exception.RegistrationException;
import crio.bookrentalsystem.rentread.exception.RentException;
import crio.bookrentalsystem.rentread.model.Book;
import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.repository.IBookRepositary;
import crio.bookrentalsystem.rentread.repository.IUserRepository;
import crio.bookrentalsystem.rentread.utils.ValidationChecks;

@Service
public class BookService {
    @Autowired
    private IBookRepositary bookRepositary;

    @Autowired
    private IUserRepository userRepository;


    public Book registerBookS(String bookTitle, String bookAuthor, String bookGenre, String bookStatus) {
        try {
           
            if(!ValidationChecks.namePatternCheck(bookTitle)){
                throw new RegistrationException("Book title should only have alphabets and spaces and cannot be empty or null");
            }

            if(!ValidationChecks.namePatternCheck(bookAuthor)){
                throw new RegistrationException("Book author should only have alphabets and spaces and cannot be empty or null");
            }

            if(!ValidationChecks.namePatternCheck(bookGenre)){
                throw new RegistrationException("Book genre should only have alphabets and spaces and cannot be empty or null");
            }

            if(!(bookStatus.equals(BookStatus.AVAILABLE.toString())) && !(bookStatus.equals(BookStatus.NOT_AVAILABLE.toString()))){
                throw new RegistrationException("Book status only allow AVAILABLE and NOT_AVAILABLE");
            }

            // if(!("ADMIN".equals(user.get().getRole()))){
            //     throw new RegistrationException("User is not having authority to register");
            // }
            
            Book book = new Book();
            book.setBookTitle(bookTitle);
            book.setBookGenre(bookGenre);
            book.setBookAuthor(bookAuthor);
            book.setBookStatus(bookStatus);

            bookRepositary.save(book);

            return book;


        } catch (Exception e) {
            throw new RegistrationException(e.getMessage());
        }
    }


    public Book updateBookS(int id, String bookTitle, String bookAuthor, String bookGenre, String bookStatus) {
        try {

            Optional<Book> fBook = bookRepositary.findById(id);

            if(!fBook.isPresent()){
                throw new FieldException("Book id does not exist");
            }

            if(!ValidationChecks.namePatternCheck(bookTitle)){
                throw new FieldException("Book title should only have alphabets and spaces and cannot be empty or null");
            }

            if(!ValidationChecks.namePatternCheck(bookAuthor)){
                throw new FieldException("Book author should only have alphabets and spaces and cannot be empty or null");
            }

            if(!ValidationChecks.namePatternCheck(bookGenre)){
                throw new FieldException("Book genre should only have alphabets and spaces and cannot be empty or null");
            }

            if(!(bookStatus.equals(BookStatus.AVAILABLE.toString())) && !(bookStatus.equals(BookStatus.NOT_AVAILABLE.toString()))){
                throw new FieldException("Book status only allow AVAILABLE and NOT_AVAILABLE");
            }
            
            fBook.get().setBookTitle(bookTitle);
            fBook.get().setBookGenre(bookGenre);
            fBook.get().setBookAuthor(bookAuthor);
            fBook.get().setBookStatus(bookStatus);

            bookRepositary.save(fBook.get());

            return fBook.get();


        } catch (Exception e) {
            throw new FieldException(e.getMessage());
        }
    }


    public boolean deleteBookS(int id) {
        Optional<Book> fBook = bookRepositary.findById(id);
        try {

            if(!fBook.isPresent()){
                throw new FieldException("Book id does not exist");
            }
    
            bookRepositary.deleteById(id);
            return true;
            
            
        } catch (Exception e) {
            throw new FieldException(e.getMessage());
        }

    }


    public List<Book> getAvailableBookS() {
        List<Book> fBook = bookRepositary.findByBookStatus("AVAILABLE");
        
        try {
            if(fBook.isEmpty()){
                throw new NotFoundException("No book is available");
            }

            return fBook;
            
            
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }


    public RentResponse rentBookS(int bookId, String username) {
        try {
            
            Optional<Book> fBook = bookRepositary.findById(bookId);

            if(!fBook.isPresent()){
                throw new RegistrationException("Book id does not exist");
            }
            // selected book is available or not
            if(fBook.get().getBookStatus().equals(BookStatus.NOT_AVAILABLE.toString())){
                throw new RentException("Selected book not is not Available");
            }

            Optional<User> user = userRepository.findById(username);
            if(!user.isPresent()){
                throw new RegistrationException("username is incorrect, please pass registered username");
            }

            List<Book> rentedBooks = user.get().getUserBooks();

            if(rentedBooks == null){
                rentedBooks = new ArrayList<>();
            }

            //Check less than 2 books are present
            if(rentedBooks.size()==2){
                throw new RentException("Maximum 2 books can be rented by a user");
            }

            rentedBooks.add(fBook.get());
            userRepository.save(user.get());

            fBook.get().setBookStatus("NOT_AVAILABLE");
            bookRepositary.save(fBook.get());

            RentResponse rentResponse = new RentResponse(fBook.get().getBookId(), fBook.get().getBookTitle(), fBook.get().getBookAuthor(), fBook.get().getBookGenre(), user.get().getUsername(), user.get().getFirstname(), user.get().getLastname());
            return rentResponse;

        }catch (Exception e) {
            throw new RentException(e.getMessage());
        }

    }


    public StatusMessage unrentBookS(int bookId, String username) {
        try {

            boolean flag = false;

            Optional<Book> fBook = bookRepositary.findById(bookId);

            if(!fBook.isPresent()){
                throw new RegistrationException("Book id does not exist");
            }
            // selected book is available or not
            if(fBook.get().getBookStatus().equals(BookStatus.AVAILABLE.toString())){
                throw new RentException("Selected book is not rented");
            }

            Optional<User> user = userRepository.findById(username);
            if(!user.isPresent()){
                throw new RegistrationException("username is incorrect, please pass registered username");
            }

            List<Book> rentedBooks = user.get().getUserBooks();

            if(rentedBooks == null){
                throw new RentException("No book is rented by the user");
            }

            for(Book book : rentedBooks){
                if(book.getBookId() == fBook.get().getBookId()){

                    rentedBooks.remove(book);
                    userRepository.save(user.get());

                    fBook.get().setBookStatus("AVAILABLE");
                    bookRepositary.save(fBook.get());
                    flag = true;
                    break;
                }
            }

            if(flag == false){
                throw new RentException("Selected book is not rented by respective user");
            }

            return new StatusMessage("Book is successfully returned by user");

        } catch (Exception e) {
            return new StatusMessage(e.getMessage());
        }

    }


    public List<Book> getAllBookS() {
        List<Book> fBook = bookRepositary.findAll();
        
        try {
            if(fBook.isEmpty()){
                throw new NotFoundException("No book found");
            }

            return fBook;
            
            
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    
}
