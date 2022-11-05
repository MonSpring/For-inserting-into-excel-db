package com.example.poiprj.repository;

import com.example.poiprj.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExcelRepository extends JpaRepository<Members, Long> {

}
