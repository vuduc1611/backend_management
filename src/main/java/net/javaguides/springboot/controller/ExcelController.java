package net.javaguides.springboot.controller;

import net.javaguides.springboot.Dto.MessageResponse;
import net.javaguides.springboot.helper.ExcelUpload;
import net.javaguides.springboot.service.impl.excelToEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "${FE_API_URL}")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private excelToEmployees excelToEmployees;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')" )
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        this.excelToEmployees.save(file);
        return ResponseEntity
                .ok(Map.of("Message" , " Customers data uploaded and saved to database successfully"));
    }
}
