package com.example.poiprj.dto.res;

import com.example.poiprj.domain.Books;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Getter
@NoArgsConstructor
public class BooksResDto {

    private Long isbn13;

    @Column
    private String bookname;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private String publication_year;

    @Column
    private int addition_symbol;

    @Column
    private Date reg_date;

    @Column
    private String bookImageURL;

    public void update (Books books) {
        this.isbn13 = books.getIsbn13();
        this.bookname = books.getBookname();
        this.author = books.getAuthor();
        this.publisher = books.getPublisher();
        this.publication_year = books.getPublication_year();
        this.addition_symbol = books.getAddition_symbol();
        this.reg_date = books.getReg_date();
        this.bookImageURL = books.getBookImageURL();
    }
}
