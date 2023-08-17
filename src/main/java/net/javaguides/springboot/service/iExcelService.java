package net.javaguides.springboot.service;

import org.springframework.web.multipart.MultipartFile;

public interface iExcelService {
    void save(MultipartFile file);
}
