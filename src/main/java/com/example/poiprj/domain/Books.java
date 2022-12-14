package com.example.poiprj.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter @Setter
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
    private Long book_id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private Date publication_year;

    @Column
    private Long isbn13;

    @Column
    private String book_count;

    @Column
    private int lend_out_book_count;

    @Column
    private Date reg_date;

    @JoinColumn(name = "libcode")
    @ManyToOne(fetch = FetchType.LAZY)
    private Librarys librarys;

    @Builder
    public Books(Long book_id, String title, String author, String publisher,
                 Date publication_year, Long isbn13, String book_count, int lend_out_book_count,
                 Date reg_date, Librarys librarys) {

        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.isbn13 = isbn13;
        this.book_count = book_count;
        this.lend_out_book_count = lend_out_book_count;
        this.reg_date = reg_date;
        this.librarys = librarys;
    }
}
