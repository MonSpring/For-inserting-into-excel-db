package com.example.poiprj.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Books {

    @Id
    //     pooled-lo ID 할당 (MySQL 의 경우 SEQUENCE 쓸 수 없고, Identity 는 batch insert 불가능하다. 이를 위한 대안책. id를 1000개씩 미리 준비하므로 시퀀스가 1000씩 올라간다)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "sequence",
            parameters = {
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = SequenceStyleGenerator.DEF_SEQUENCE_NAME),
                    @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lo")
            }
    )
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

    @Builder
    public Books(Long isbn13, String bookname, String author, String publisher, String publication_year, int addition_symbol, Date reg_date, String bookImageURL) {
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
