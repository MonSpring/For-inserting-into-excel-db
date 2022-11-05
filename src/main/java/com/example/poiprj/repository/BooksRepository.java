package com.example.poiprj.repository;

import com.example.poiprj.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;

public interface BooksRepository extends JpaRepository<Books, Long> {
    Books findBy(Books books);
}
