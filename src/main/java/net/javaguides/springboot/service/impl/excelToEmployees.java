package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.helper.ExcelUpload;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.iExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class excelToEmployees implements iExcelService {

    private EmployeeRepository employeeRepository;

    @Override
    public void save(MultipartFile file) {
        if(ExcelUpload.isValidExcelFile(file)) {
            try {
                List<Employee> employees = ExcelUpload.excelToEmployees(file.getInputStream());
                employees.forEach(employee -> {
                    if(employeeRepository.existsById(employee.getId()))  throw new RuntimeException("Employee Id is exist, re-check file upload");
                });
                this.employeeRepository.saveAll(employees);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
}
