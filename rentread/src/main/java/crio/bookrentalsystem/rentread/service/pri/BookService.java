package crio.bookrentalsystem.rentread.service.pri;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.dto.BookStatus;
import crio.bookrentalsystem.rentread.exception.FieldException;
import crio.bookrentalsystem.rentread.exception.NotFoundException;
import crio.bookrentalsystem.rentread.exception.RegistrationException;
import crio.bookrentalsystem.rentread.model.Book;
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
    
}
