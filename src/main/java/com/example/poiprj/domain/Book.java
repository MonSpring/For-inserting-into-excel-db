package com.example.poiprj.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Column
    private String bookname;
    @Column
    private String author;
    @Column
    private String publisher;
    @Column
    private String publication_year;
    @Column
    private String vol;
    @Id @Column
    private Integer ISBN;
    @Column
    private Integer numberOfBooks;

    @Column
    private Integer numberOfLoans;

    @Column
    private Date reg_date;

    @Builder
    public Book(String bookname, String author, String publisher, String publication_year, String vol, Integer ISBN, Integer numberOfBooks, Integer numberOfLoans, Date reg_date) {
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.vol = vol;
        this.ISBN = ISBN;
        this.numberOfBooks = numberOfBooks;
        this.numberOfLoans = numberOfLoans;
        this.reg_date = reg_date;
    }
}
