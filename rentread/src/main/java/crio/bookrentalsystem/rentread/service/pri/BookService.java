package crio.bookrentalsystem.rentread.service.pri;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.dto.BookStatus;
import crio.bookrentalsystem.rentread.exception.RegistrationException;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;


    public Book registerBookS(String bookTitle, String bookAuthor, String bookGenre, String bookStatus) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();


            Optional<User> user = userRepository.findById(username);
            if(!user.isPresent()){
                throw new RegistrationException("The username provided do not exit");
            }

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

            if(!("ADMIN".equals(user.get().getRole()))){
                throw new RegistrationException("User is not having authority to register");
            }
            
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
    
}
