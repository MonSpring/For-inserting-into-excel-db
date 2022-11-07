package com.example.poiprj.service;

import com.example.poiprj.domain.Books;
import com.example.poiprj.dto.req.ExcelData;
import com.example.poiprj.repository.ExcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.Library;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ExcelRepository excelRepository;
//    private final LibrarysRepository librarysRepository;


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

    @Transactional
    public void updateDatabase(MultipartFile file, long code) throws IOException, ParseException {

//        , int libcode

//        Long libcodeTemp = (long) libcode;

//        Librarys librarys = librarysRepository.findById(libcodeTemp).orElseThrow(()->new RuntimeException("도서관을 찾을 수 없습니다"));

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
            if (row.getCell(1)==null) {
                // 타이틀이 빈셀이면 i++ 하고 돌아가
                continue;
            }

            String publication_year_temp = row.getCell(4).getStringCellValue();
            SimpleDateFormat publication_year_formatter = new SimpleDateFormat("yyyy");
            Date publication_year = publication_year_formatter.parse(publication_year_temp);

            String reg_date_temp = row.getCell(12).getStringCellValue();
            SimpleDateFormat regdate_formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date reg_date = regdate_formatter.parse(reg_date_temp);


            Books books = Books.builder()
//                    .librarys(librarys)
                    .title(row.getCell(1).getStringCellValue())
                    .author(row.getCell(2).getStringCellValue())
                    .publisher(row.getCell(3).getStringCellValue())
                    .publication_year(publication_year)
                    .isbn13(Long.parseLong(row.getCell(5).getStringCellValue()))
                    .book_count(Integer.parseInt(row.getCell(10).getStringCellValue()))
                    .lend_out_book_count(Integer.parseInt(row.getCell(11).getStringCellValue()))
                    .reg_date(reg_date)
                    .build();

            booksList.add(books);
        }
        excelRepository.saveAll(booksList);

    }

//    public void updateDatabaseLib(MultipartFile file) throws IOException {
//
//        List<Librarys> librarysList = new ArrayList<>();
//
//        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3
//
//        if (!extension.equals("xlsx") && !extension.equals("xls")) {
//            throw new IOException("엑셀파일만 업로드 해주세요.");
//        }
//
//        Workbook workbook = null;
//        if (extension.equals("xlsx")) {
//            workbook = new XSSFWorkbook(file.getInputStream());
//        } else if (extension.equals("xls")) {
//            workbook = new HSSFWorkbook(file.getInputStream());
//        }
//
//        Sheet worksheet = workbook.getSheetAt(0);
//
//        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
//
//            Row row = worksheet.getRow(i);
//
//            // 빈셀 확인
//            if (row.getCell(1)==null) {
//                continue;
//            }
//
//            Librarys librarys = Librarys.builder()
//                    .build();
//
//            librarysList.add(Librarys);
//        }
//        excelRepository.saveAll(librarysList);
//    }

}


