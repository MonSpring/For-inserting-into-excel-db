package com.example.poiprj.service;

import com.example.poiprj.domain.Books;
import com.example.poiprj.domain.Librarys;
import com.example.poiprj.dto.req.ExcelData;
import com.example.poiprj.repository.BooksRepository;
import com.example.poiprj.repository.LibrarysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import java.util.regex.Pattern;

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

            //GET CELL
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(10);
            Cell cell7 = row.getCell(11);
            Cell cell8 = row.getCell(12);

            if(cell4==null) {
                continue;
            }

            //SET AS STRING TYPE
            cell1.setCellType(Cell.CELL_TYPE_STRING);
            cell2.setCellType(Cell.CELL_TYPE_STRING);
            cell3.setCellType(Cell.CELL_TYPE_STRING);
            cell4.setCellType(Cell.CELL_TYPE_STRING);
            cell5.setCellType(Cell.CELL_TYPE_STRING);
            cell6.setCellType(Cell.CELL_TYPE_STRING);
            cell7.setCellType(Cell.CELL_TYPE_STRING);
            cell8.setCellType(Cell.CELL_TYPE_STRING);

            //Get As String TYPE
            String data1= cell1.getStringCellValue();
            String data2= cell2.getStringCellValue();
            String data3= cell3.getStringCellValue();
            String data4= cell4.getStringCellValue();
            String data5= cell5.getStringCellValue();
            String data6= cell6.getStringCellValue();
            String data7= cell7.getStringCellValue();
            String data8= cell8.getStringCellValue();

            // 빈셀 확인
            if (data4.equals("")) {
                // 날짜가 빈셀이면 i++ 하고 돌아가
                continue;
            }

            // ISBN 앞 공백 제거
            if (data5.startsWith(" ")) {
                data5 = data5.substring(1);
            }

            // ISBN X 제거
            if (data5.endsWith("X")) {
                data5 = data5.substring(0, data5.length()-1);
            }

            // 이상한 셀 확인 (ex. c2014)
            if (data4.startsWith("c")) {
                data4 = data4.substring(1);
            }

            if (data4.contains("nyu")) {
                data4 = "2000";
            }

            if (data2.length() > 120) {
                continue;
            }

            // 정규식
            boolean myPattern = Pattern.matches("^[0-9]{4}$", data4);
            if (!myPattern) {
                continue;
            }

            SimpleDateFormat publication_year_formatter = new SimpleDateFormat("yyyy");
            Date publication_year = publication_year_formatter.parse(data4);

            SimpleDateFormat regdate_formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date reg_date = regdate_formatter.parse(data8);

            Books books = Books.builder()
                    .librarys(librarys)
                    .title(data1)
                    .author(data2)
                    .publisher(data3)
                    .publication_year(publication_year)
                    .isbn13(Long.parseLong(data5))
                    .book_count(data6)
                    .lend_out_book_count(Integer.parseInt(data7))
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


