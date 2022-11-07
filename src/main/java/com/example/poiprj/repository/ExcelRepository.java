package com.example.poiprj.repository;

import com.example.poiprj.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelRepository extends JpaRepository<Books, Long> {

}
