package com.example.poiprj.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResTestDto {
    private String title;
    private String author;
    private String publisher;
    private String publication_year;
    private String vol;
    private Integer ISBN;
    private Integer numberOfBooks;
    private Integer numberOfLoans;
    private Date reg_date;

}
