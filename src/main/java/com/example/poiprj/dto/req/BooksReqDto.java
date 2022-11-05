package com.example.poiprj.dto.req;

import com.example.poiprj.domain.Books;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Getter
@NoArgsConstructor
public class BooksReqDto {

    private Long isbn13;

    private String bookname;

    private String author;

    private String publisher;

    private String publication_year;

    private int addition_symbol;

    private Date reg_date;

    private String bookImageURL;

    @Builder
    public BooksReqDto(Long isbn13, String bookname, String author, String publisher, String publication_year, int addition_symbol, Date reg_date, String bookImageURL) {
        this.isbn13 = isbn13;
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.addition_symbol = addition_symbol;
        this.reg_date = reg_date;
        this.bookImageURL = bookImageURL;
    }
}
