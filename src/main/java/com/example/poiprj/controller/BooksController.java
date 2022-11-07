package com.example.poiprj.controller;

import com.example.poiprj.dto.req.BooksReqDto;
import com.example.poiprj.dto.res.BooksResDto;
import com.example.poiprj.service.BooksService;
import com.example.poiprj.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @GetMapping("")
    public List<BooksResDto> getBooksList() {
        return booksService.getBooksList();
    }

    @PostMapping("")
    public ResponseDto<?> setBooks(BooksReqDto booksReqDto) {
        return booksService.setBooks(booksReqDto);
    }

}
