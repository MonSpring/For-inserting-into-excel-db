package com.example.poiprj.dto.res;

import com.example.poiprj.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResTestDto {
    private String bookname;
    private String author;
    private String publisher;
    private String publication_year;
    private String vol;
    private Integer ISBN;
    private Integer numberOfBooks;
    private Integer numberOfLoans;
    private Date reg_date;

    public BookResTestDto(Book book) {
        this.bookname = book.getBookname();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.publication_year = book.getPublication_year();
        this.vol = book.getVol();
        this.ISBN = book.getISBN();
        this.numberOfBooks = book.getNumberOfBooks();
        this.numberOfLoans = book.getNumberOfLoans();
        this.reg_date = book.getReg_date();
    }
}
