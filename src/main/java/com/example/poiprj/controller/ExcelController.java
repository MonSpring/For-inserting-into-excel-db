package com.example.poiprj.controller;

import java.io.IOException;
import java.text.ParseException;

import com.example.poiprj.customAnnotation.LogExecutionTime;
import com.example.poiprj.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/excel")
    public String main() { // 1
        return "excel";
    }

    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        model.addAttribute("datas", excelService.updateView(file)); // 5
        return "excelList";
    }

    // 도서 정보 엑셀파일 업로드
    @LogExecutionTime
    @PostMapping("/excel/read/upload")
    public String readAndUploadExcelBook(@RequestParam("file") MultipartFile file,
                                         @RequestParam("libcode") long code)
            throws IOException, ParseException {
//        , Librarys librarys
        excelService.updateDatabase(file, code);
//        , libcode
        return "upload";
    }

    // 도서 정보 엑셀파일 업로드
//    @LogExecutionTime
//    @PostMapping("excel/read/uploadlibrary")
//    public String readAndUploadExcelLib(@RequestParam("file") MultipartFile file) throws IOException {
//        excelService.updateDatabaseLib(file);
//        return "upload";
//    }
}