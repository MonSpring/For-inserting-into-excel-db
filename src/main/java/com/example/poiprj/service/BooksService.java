package com.example.poiprj.service;

import com.example.poiprj.domain.Books;
import com.example.poiprj.dto.ResponseDto;
import com.example.poiprj.dto.req.BooksReqDto;
import com.example.poiprj.dto.res.BooksResDto;
import com.example.poiprj.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public List<BooksResDto> getBooksList() {
        List<BooksResDto> booksResDtoList = new ArrayList<>();
        List<Books> booksList= booksRepository.findAll();
        for (Books books:booksList) {
            BooksResDto booksResDto = new BooksResDto();
            booksResDto.update(books);
            booksResDtoList.add(booksResDto);
        }
        return booksResDtoList;
    }

    public ResponseDto<?> setBooks(BooksReqDto booksReqDto) {

            Books books = Books.builder()
                    .isbn13(booksReqDto.getIsbn13())
                    .reg_date(booksReqDto.getReg_date())
                    .publisher(booksReqDto.getPublisher())
                    .publication_year(booksReqDto.getPublication_year())
                    .title(booksReqDto.getTitle())
                    .author(booksReqDto.getAuthor())
                    .build();

        return ResponseDto.success("책 등록 완료");
    }
}
