package com.example.poiprj.controller;

import java.io.IOException;

import com.example.poiprj.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("datas", excelService.updateView(file, model)); // 5
        return "excelList";
    }

    @PostMapping("/excel/read/upload")
    public String readAndUploadExcel(@RequestParam("file") MultipartFile file)
            throws IOException { // 2
        excelService.updateDatabase(file);
        return "upload";
    }
}