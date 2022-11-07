package com.example.poiprj.service;

import com.example.poiprj.domain.Books;
import com.example.poiprj.domain.Librarys;
import com.example.poiprj.dto.req.ExcelData;
import com.example.poiprj.repository.BooksRepository;
import com.example.poiprj.repository.LibrarysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelService {
    private final LibrarysRepository librarysRepository;
    private final BooksRepository booksRepository;


    public List<ExcelData> updateView(MultipartFile file) throws IOException {
        List<ExcelData> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelData data = new ExcelData();

            data.setNum((int) row.getCell(0).getNumericCellValue());
            data.setName(row.getCell(1).getStringCellValue());
            data.setEmail(row.getCell(2).getStringCellValue());

            dataList.add(data);
        }

        return dataList;
    }

    public void updateDatabase(MultipartFile file, long code) throws IOException, ParseException {

        Long libcodeTemp = code;

        Librarys librarys = librarysRepository.findById(libcodeTemp).orElseThrow(()->new RuntimeException("도서관을 찾을 수 없습니다"));

        List<Books> booksList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            // 빈셀 확인
            if (Objects.equals(row.getCell(4).getStringCellValue(), "")) {
                // 날짜가 빈셀이면 i++ 하고 돌아가
                continue;
            }

            String publication_year_temp = row.getCell(4).getStringCellValue();
            SimpleDateFormat publication_year_formatter = new SimpleDateFormat("yyyy");
            Date publication_year = publication_year_formatter.parse(publication_year_temp);

            String reg_date_temp = row.getCell(12).getStringCellValue();
            SimpleDateFormat regdate_formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date reg_date = regdate_formatter.parse(reg_date_temp);


            Books books = Books.builder()
                    .librarys(librarys)
                    .title(row.getCell(1).getStringCellValue())
                    .author(row.getCell(2).getStringCellValue())
                    .publisher(row.getCell(3).getStringCellValue())
                    .publication_year(publication_year)
                    .isbn13(Long.parseLong(row.getCell(5).getStringCellValue()))
                    .book_count(row.getCell(10).getStringCellValue())
                    .lend_out_book_count(Integer.parseInt(row.getCell(11).getStringCellValue()))
                    .reg_date(reg_date)
                    .build();

            booksList.add(books);
        }
        booksRepository.saveAll(booksList);

    }

    public void updateDatabaseLib(MultipartFile file) throws IOException {

        List<Librarys> librarysList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 8; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            // 빈셀 확인
            if (Objects.equals(row.getCell(1).getStringCellValue(), "")) {
                continue;
            }

            Librarys librarys = Librarys.builder()
                    .libName(row.getCell(0).getStringCellValue())
                    .address(row.getCell(1).getStringCellValue())
                    .tel(row.getCell(2).getStringCellValue())
                    .fax(row.getCell(3).getStringCellValue())
                    .latitude(Float.parseFloat(row.getCell(4).getStringCellValue()))
                    .longitude(Float.parseFloat(row.getCell(5).getStringCellValue()))
                    .homepage(row.getCell(6).getStringCellValue())
                    .closed(row.getCell(8).getStringCellValue())
                    .libcode(Long.parseLong(row.getCell(9).getStringCellValue()))
                    .build();

            librarysList.add(librarys);
        }
        librarysRepository.saveAll(librarysList);
    }

}


