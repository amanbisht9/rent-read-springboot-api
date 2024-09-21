package crio.bookrentalsystem.rentread.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentResponse {

    private int bookId;
    
    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private String username;
    private String firstname;
    private String lastname;

    
}
