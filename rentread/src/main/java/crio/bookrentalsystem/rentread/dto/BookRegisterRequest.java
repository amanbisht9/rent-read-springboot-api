package crio.bookrentalsystem.rentread.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterRequest {

    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private String bookStatus;
    
}
