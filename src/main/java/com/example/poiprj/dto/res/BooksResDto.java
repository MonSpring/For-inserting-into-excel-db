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

    private String title;

    private String author;

    private String publisher;

    private Date publication_year;

    private Date reg_date;

    public void update (Books books) {
        this.isbn13 = books.getIsbn13();
        this.title = books.getTitle();
        this.author = books.getAuthor();
        this.publisher = books.getPublisher();
        this.publication_year = books.getPublication_year();
        this.reg_date = books.getReg_date();
    }
}
