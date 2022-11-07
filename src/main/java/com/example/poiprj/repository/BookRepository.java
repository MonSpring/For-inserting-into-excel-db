package com.example.poiprj.repository;

import com.example.poiprj.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    //Books findByBooks(Books books);
}
